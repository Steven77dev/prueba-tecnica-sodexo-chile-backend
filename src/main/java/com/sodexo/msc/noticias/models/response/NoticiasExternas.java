package com.sodexo.msc.noticias.models.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticiasExternas {
    private int id;
    private String title;
    private String url;
    private String image_url;
    private String news_site;
    private String summary;
    private String published_at;
    private String updated_at;
    private boolean featured;
}
