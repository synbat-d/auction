package kz.redmadbot.auction.service;

import kz.redmadbot.auction.DTO.AnnouncementDto;
import kz.redmadbot.auction.entity.Announcement;
import kz.redmadbot.auction.entity.User;
import kz.redmadbot.auction.repository.AnnouncementRepository;
import kz.redmadbot.auction.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementServiceImpl implements AnnouncementService{
    @Value("${interval}")
    private String interval;
    @Autowired
    AnnouncementRepository announcementRepository;

    @Autowired
    UserRepository userRepository;
    @Override
    public void save(AnnouncementDto announcementDto) {
        Announcement announcement = new Announcement();
        announcement.setProductName(announcementDto.getProductName());
        announcement.setProductDescription(announcementDto.getProductDescription());
        announcement.setPictureUrl(announcementDto.getPictureUrl());
        announcement.setStatus(true);
        announcement.setMinCost(announcementDto.getMinCost());
        announcement.setCurrentCost(announcementDto.getMinCost());
        announcement.setCreatedAt(new Date(System.currentTimeMillis()));
        String ownerEmail = getEmailOfCurrentlyLoggedInUser();
        User ownerUser = userRepository.findByEmail(ownerEmail).orElseThrow();
        announcement.setOwnerUser(ownerUser);
        announcementRepository.save(announcement);
    }

    @Override
    public List<AnnouncementDto> getAllActive() {
        List<AnnouncementDto> announcementDtos = new ArrayList<>();
        for (Announcement element: announcementRepository.findAllByStatusIs(true)) {
            announcementDtos.add(new AnnouncementDto(element.getId(), element.getOwnerUser().getEmail(), element.getProductName(),
                    element.getProductDescription(), element.getPictureUrl(),
                    element.getMinCost(), element.getCurrentCost()));
        }
        return announcementDtos;
    }

    @Override
    public List<AnnouncementDto> getMyActiveAnnouncements() {
        return null;
    }

    @Override
    public List<AnnouncementDto> getMyAttemptedAnnouncements() {
        return null;
    }

    @Override
    public boolean suggestingPrice(long id, int cost) {
        Optional<Announcement> targettedAnnouncement = announcementRepository.findById(id);
        String currentEmail = getEmailOfCurrentlyLoggedInUser();
        if(targettedAnnouncement.isPresent()) {
            Announcement announcement = targettedAnnouncement.get();
            if(!announcement.getOwnerUser().getEmail().equals(currentEmail)
                    && announcement.getStatus()
                    && cost>announcement.getCurrentCost()) {
                announcement.setCurrentCost(cost);
                announcement.setBuyerUser(userRepository.findByEmail(currentEmail).orElseThrow());
                announcement.setCreatedAt(new Date(System.currentTimeMillis()));
                announcementRepository.save(announcement);
                return true;
            }
            return false;
        }else {
            return false;
        }
    }

    private String getEmailOfCurrentlyLoggedInUser() {
        String email=null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        }
        else {
            email = principal.toString();
        }
        return email;
    }

    @Scheduled(fixedRateString = "${interval}")
    public void checkAnnouncements() {
        List<Announcement> announcements = announcementRepository.findAllByStatusIsTrueAndAndBuyerUserIsNotNull();
        for (Announcement element: announcements) {
            long difference = System.currentTimeMillis()-element.getCreatedAt().getTime();

            if(difference>=Duration.parse(interval).toMillis()) {
                element.setStatus(false);
                System.out.println("Hi I am making some changes to db");
                announcementRepository.save(element);
                //you have send email to owner and buyer
            }
        }
    }
}
