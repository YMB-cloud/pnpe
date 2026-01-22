/*
   Copyright 2009-2022 PrimeTek.

   Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package org.primefaces.siberia.view;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

@Named
@SessionScoped
public class GuestPreferences implements Serializable {

    //private String menuMode = "layout-overlay";
	private String menuMode = "layout-overlay";

    private String layout = "yana";
    //private String layout = "caspian";
    
    //private String theme = "cyan";
    private String theme = "bluegrey";

    private String inputStyle = "outlined";

    private boolean whiteLogo = true;

    private boolean groupedMenu = false;

    private List<LayoutThemeDark> layoutThemesDark;

    private List<LayoutThemeLight> layoutThemesLight;

    private List<ComponentTheme> componentThemes;

    @PostConstruct
    public void init() {
        layoutThemesDark = new ArrayList<>();
        layoutThemesDark.add(new LayoutThemeDark("Dark", "dark", "dark.png","indigo",true));
        layoutThemesDark.add(new LayoutThemeDark("Island", "island", "island.png","yellow",true));
        layoutThemesDark.add(new LayoutThemeDark("Ural", "ural", "ural.png","bluegrey",true));
        layoutThemesDark.add(new LayoutThemeDark("Moss", "moss", "moss.png","cyan",true));
        layoutThemesDark.add(new LayoutThemeDark("Altai", "altai", "altai.png","purple",true));
        layoutThemesDark.add(new LayoutThemeDark("Arctic", "arctic", "arctic.png","blue",true));
        layoutThemesDark.add(new LayoutThemeDark("Baikal", "baikal", "baikal.png","green",true));
        layoutThemesDark.add(new LayoutThemeDark("Lena", "lena", "lena.png","purple",true));
        layoutThemesDark.add(new LayoutThemeDark("Yana", "yana", "yana.png","cyan",true));
        layoutThemesDark.add(new LayoutThemeDark("Tiger", "tiger", "tiger.png","red",true));
        layoutThemesDark.add(new LayoutThemeDark("Chita", "chita", "chita.png","purple",true));
        layoutThemesDark.add(new LayoutThemeDark("Kolyma", "kolyma", "kolyma.png","deeppurple",true));
        layoutThemesDark.add(new LayoutThemeDark("Caspian", "caspian", "caspian.png","bluegrey",true));
        layoutThemesDark.add(new LayoutThemeDark("Tomsk", "tomsk", "tomsk.png","red",true));
        layoutThemesDark.add(new LayoutThemeDark("Barnaul", "barnaul", "barnaul.png","indigo",true));
        layoutThemesDark.add(new LayoutThemeDark("Magadan", "magadan", "magadan.png","brown",true));
        layoutThemesDark.add(new LayoutThemeDark("Omsk", "omsk", "omsk.png","purple",true));
        layoutThemesDark.add(new LayoutThemeDark("Kyzyl", "kyzyl", "kyzyl.png","purple",true));

        layoutThemesLight = new ArrayList<>();
        layoutThemesLight.add(new LayoutThemeLight("Light", "light", "light.png","turquoise",false));
        layoutThemesLight.add(new LayoutThemeLight("Mansi", "mansi", "mansi.png","red",false));
        layoutThemesLight.add(new LayoutThemeLight("Volga", "volga", "volga.png","bluegrey",false));
        layoutThemesLight.add(new LayoutThemeLight("Sakha", "sakha", "sakha.png","cyan",true));
        layoutThemesLight.add(new LayoutThemeLight("Anadyr", "anadyr", "anadyr.png","turquoise",false));
        layoutThemesLight.add(new LayoutThemeLight("Kurgan", "kurgan", "kurgan.png","amber",false));
        layoutThemesLight.add(new LayoutThemeLight("Tuva", "tuva", "tuva.png","pink",false));
        layoutThemesLight.add(new LayoutThemeLight("Yakut", "yakut", "yakut.png","green",false));
        layoutThemesLight.add(new LayoutThemeLight("North", "north", "north.png","bluegrey",false));
        layoutThemesLight.add(new LayoutThemeLight("Uvs", "uvs", "uvs.png","purple",true));
        layoutThemesLight.add(new LayoutThemeLight("Yenise", "yenise", "yenise.png","indigo",true));

        componentThemes = new ArrayList<>();
        componentThemes.add(new ComponentTheme("Turquoise", "turquoise", "turquoise.png"));
        componentThemes.add(new ComponentTheme("Amber", "amber", "amber.png"));
        componentThemes.add(new ComponentTheme("Red", "red", "red.png"));
        componentThemes.add(new ComponentTheme("Pink", "pink", "pink.png"));
        componentThemes.add(new ComponentTheme("Yellow", "yellow", "yellow.png"));
        componentThemes.add(new ComponentTheme("Brown", "brown", "brown.png"));
        componentThemes.add(new ComponentTheme("Teal", "teal", "teal.png"));
        componentThemes.add(new ComponentTheme("Deep Purple", "deeppurple", "deeppurple.png"));
        componentThemes.add(new ComponentTheme("Blue", "blue", "blue.png"));
        componentThemes.add(new ComponentTheme("Indigo", "indigo", "indigo.png"));
        componentThemes.add(new ComponentTheme("Lime", "lime", "lime.png"));
        componentThemes.add(new ComponentTheme("Green", "green", "green.png"));
        componentThemes.add(new ComponentTheme("Purple", "purple", "purple.png"));
        componentThemes.add(new ComponentTheme("Blue Grey", "bluegrey", "bluegrey.png"));
        componentThemes.add(new ComponentTheme("Cyan", "cyan", "cyan.png"));
    }

    public String getLayout() {
        return layout;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setLayout(String layout , String theme, boolean logo) {
        this.layout = layout;
        this.theme = theme;
        this.whiteLogo = logo;
    }

    public String getInputStyle() {
        return inputStyle;
    }

    public void setInputStyle(String inputStyle) {
        this.inputStyle = inputStyle;
    }

    public String getInputStyleClass() {
        return this.inputStyle.equals("filled") ? "ui-input-filled" : "";
    }

    public boolean isWhiteLogo() {
        return this.whiteLogo;
    }

    public String getMenuMode() {
        return this.menuMode;
    }

    public boolean isGroupedMenu() {
        return this.groupedMenu;
    }

    public void setGroupedMenu(boolean value) {
        this.groupedMenu = value;
    }

    public void setMenuMode(String menuMode) {
        this.menuMode = menuMode;
        
        if (this.menuMode.equals("layout-megamenu")) {
            this.groupedMenu = true;
        }
    }
    
    public List<LayoutThemeDark> getLayoutThemesDark() {
        return layoutThemesDark;
    }

    public List<LayoutThemeLight> getLayoutThemesLight() {
        return layoutThemesLight;
    }

    public List<ComponentTheme> getComponentThemes() {
        return componentThemes;
    }

    public class LayoutThemeDark {
        String name;
        String file;
        String image;
        String theme;
        boolean logo;

        public LayoutThemeDark(String name, String file, String image, String theme,boolean logo) {
            this.name = name;
            this.file = file;
            this.image = image;
            this.theme = theme;
            this.logo = logo;
        }

        public String getName() {
            return this.name;
        }

        public String getFile() {
            return this.file;
        }

        public String getImage() {
            return this.image;
        }
        
        public String getTheme() {
            return this.theme;
        }

        public boolean getLogo() {
            return this.logo;
        }
    }

    public class LayoutThemeLight {
        String name;
        String file;
        String image;
        String theme;
        boolean logo;

        public LayoutThemeLight(String name, String file, String image, String theme,boolean logo) {
            this.name = name;
            this.file = file;
            this.image = image;
            this.theme = theme;
            this.logo = logo;
        }

        public String getName() {
            return this.name;
        }

        public String getFile() {
            return this.file;
        }

        public String getImage() {
            return this.image;
        }
        
        public String getTheme() {
            return this.theme;
        }

        public boolean getLogo() {
            return this.logo;
        }
    }

    public class ComponentTheme {
        String name;
        String file;
        String image;

        public ComponentTheme(String name, String file, String image) {
            this.name = name;
            this.file = file;
            this.image = image;
        }

        public String getName() {
            return this.name;
        }

        public String getFile() {
            return this.file;
        }

        public String getImage() {
            return this.image;
        }
    }

}
