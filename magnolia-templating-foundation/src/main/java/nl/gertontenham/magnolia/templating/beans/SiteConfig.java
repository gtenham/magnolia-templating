package nl.gertontenham.magnolia.templating.beans;

/**
 * Site node2bean configuration class
 */
public class SiteConfig {

    private String name;
    private String sitePageMap;
    private String siteTitle;
    private String siteSlogan;
    private String siteLogoImg;
    private String siteAlternativeLogoText;
    private String gaAccount;
    private Boolean sslEnabled;
    private String mappedServerName;
    private String mappedServerPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSitePageMap() {
        return sitePageMap;
    }

    public void setSitePageMap(String sitePageMap) {
        this.sitePageMap = sitePageMap;
    }

    public String getSiteTitle() {
        return siteTitle;
    }

    public void setSiteTitle(String siteTitle) {
        this.siteTitle = siteTitle;
    }

    public String getSiteSlogan() {
        return siteSlogan;
    }

    public void setSiteSlogan(String siteSlogan) {
        this.siteSlogan = siteSlogan;
    }

    public String getSiteLogoImg() {
        return siteLogoImg;
    }

    public void setSiteLogoImg(String siteLogoImg) {
        this.siteLogoImg = siteLogoImg;
    }

    public String getSiteAlternativeLogoText() {
        return siteAlternativeLogoText;
    }

    public void setSiteAlternativeLogoText(String siteAlternativeLogoText) {
        this.siteAlternativeLogoText = siteAlternativeLogoText;
    }

    public Boolean getSslEnabled() {
        return sslEnabled;
    }

    public void setSslEnabled(Boolean sslEnabled) {
        this.sslEnabled = sslEnabled;
    }

    public String getGaAccount() {
        return gaAccount;
    }

    public void setGaAccount(String gaAccount) {
        this.gaAccount = gaAccount;
    }

    public String getMappedServerName() {
        return mappedServerName;
    }

    public void setMappedServerName(String mappedServerName) {
        this.mappedServerName = mappedServerName;
    }

    public String getMappedServerPath() {
        return mappedServerPath;
    }

    public void setMappedServerPath(String mappedServerPath) {
        this.mappedServerPath = mappedServerPath;
    }
}
