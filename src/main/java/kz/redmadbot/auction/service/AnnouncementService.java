package kz.redmadbot.auction.service;

import kz.redmadbot.auction.DTO.AnnouncementDto;

import java.util.List;

public interface AnnouncementService {
    public void save(AnnouncementDto announcementDto);
    public List<AnnouncementDto> getAllActive();

    public List<AnnouncementDto> getMyActiveAnnouncements();

    public List<AnnouncementDto> getMyAttemptedAnnouncements();

    public boolean suggestingPrice(long id, int cost);
}
