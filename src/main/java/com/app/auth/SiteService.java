package com.app.auth;

import java.util.List;

import com.app.entity.Site;
import com.app.entity.SiteDTO;

public interface SiteService {

    Site creer(SiteDTO dto);

    Site modifier(SiteDTO dto);

    void desactiver(Long siteId);

    Site trouverParId(Long id);

    List<Site> listerTous();
}
