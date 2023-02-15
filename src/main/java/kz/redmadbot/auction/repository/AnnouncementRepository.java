package kz.redmadbot.auction.repository;

import kz.redmadbot.auction.entity.Announcement;
import kz.redmadbot.auction.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository <Announcement, Long> {
    public List<Announcement> findAllByStatusIs(boolean status);
    public List<Announcement> findAllByStatusIsTrueAndAndBuyerUserIsNotNull();
}

