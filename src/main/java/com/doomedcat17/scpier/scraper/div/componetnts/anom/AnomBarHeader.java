package com.doomedcat17.scpier.scraper.div.componetnts.anom;

import com.doomedcat17.scpier.data.scp.SCPLanguage;

import java.util.Arrays;

public enum AnomBarHeader {
    ENGLISH("eng", "Item #:", "Containment Class:", "Secondary Class:", "Disrupt Class:", "Risk Class:"),
    POLISH("pl", "Identyfikator podmiotu:", "Klasa podmiotu:", "Drugorzędna klasa:", "Poziom zakłóceń:", "Poziom ryzyka:"),
    RUSSIAN("ru", "Объект №:", "КЛАСС СОДЕРЖАНИЯ:", "Вторичный класс:", "КЛАСС НАРУШЕНИЯ:", "КЛАСС РИСКА:"),
    JAPANESE("jp", "アイテム番号:", "収容クラス:", "副次クラス:", "撹乱クラス:", "リスククラス:"),
    CHINESE("cn", "项目编号：", "收容等级：", "次要等级：", "扰动等级：", "风险等级："),
    KOREAN("ko", "일련번호:", "격리 등급:", "2차 등급:", "혼란 등급:", "위험 등급:"),
    FRENCH("fr", "Objet no:", "Classe de confinement:", "Classe secondaire:", "Classe de perturbation:", "Classe de risque:"),
    SPANISH("es", "Ítem #:", "Clase de contención:", "Clase secundaria:", "Interrupción de la clase:", "Clase de riesgo:"),
    THAI("th", "วัตถุ#:", "ระดับกักกัน:", "ระดับมัธยมศึกษา:", "รบกวนชั้นเรียน:", "ระดับความเสี่ยง:"),
    GERMAN("de", "Objekt Nr.:", "Eindämmungsklasse:", "Sekundärklasse:", "Störungsklasse:", "Risikoklasse:"),
    ITALIAN("it", "Elemento #:", "Classe di Contenimento:", "Classe Secondaria:", "Classe di Disturbo:", "Classe di Rischio:"),
    UKRAINIAN("ua", "Об'єкт №:", "Клас зберігання:", "Вторинний клас:", "Клас порушення:", "Клас ризику:"),
    PORTUGUESE("pt", "Item nº:", "Classe de Contenção:", "Classe Secundária:", "Classe de Disrupção:", "Classe de Risco:"),
    CZECH("cs", "Objekt #:", "Zadržovací Třída:", "Sekundární Třída:", "Třída Narušení:", "Třída Ohrožení:"),
    GREEK("el", "Αντικείμενο #:", "Τάξη συγκράτησης:", "Δευτεροβάθμια Τάξη:", "Διαταράξτε την τάξη:", "Κατηγορία Κινδύνου:"),
    INDONESIAN("idn", "Objek #:", "Kelas Penahanan:", "Kelas Menengah:", "Mengganggu Kelas:", "Kelas Risiko:"),
    DANISH("da", "Genstand #:", "Indeslutningsklasse:", "Sekundær klasse:", "Forstyrre klasse:", "Risikoklasse:"),
    FINNISH("fo", "Esine #:", "Suojausluokka:", "Toissijainen luokka:", "Häiritä luokka:", "Riskiluokka:"),
    NORWEGIAN("no", "Objektnummer:", "Inneslutningsklasse:", "Sekundær klasse:", "Forstyrre klasse:", "Risikoklasse:"),
    SWEDISH("sv", "Föremål:", "Innehållsklass:", "Sekundärklass:", "Stör klass:", "Riskklass:"),
    VIETNAMESE("vn", "Mã vật thể:", "Phân loại Quản thúc:", "Phân loại Bổ sung:", "Phân loại Quy mô:", "Phân loại Hiểm họa:"),
    TURKISH("tr", "Madde #:", "Nesne Sinifi:", "Ikinci sınıf:", "Sekte seviyesi:", "Risk seviyesi:"),
    CHINESE_TRADITIONAL("zh", "項目編號:", "收容等級：", "次要等級：", "瓦解等級：", "風險等級：");

    public String langIdentifier;
    public String itemName;
    public String containClass;
    public String secondClass;
    public String disruptClass;
    public String riskClass;

    AnomBarHeader(String langIdentifier, String itemName, String containClass, String secondClass, String disruptClass, String riskClass) {
        this.langIdentifier = langIdentifier;
        this.itemName = itemName;
        this.containClass = containClass;
        this.secondClass = secondClass;
        this.disruptClass = disruptClass;
        this.riskClass = riskClass;
    }


    public String getByStyleName(String styleClassname) {
        if (styleClassname.contains("item")) {
            return itemName;
        } else if (styleClassname.contains("container")) {
            return containClass;
        } else if (styleClassname.contains("second")) {
            return secondClass;
        } else if (styleClassname.contains("disruption")) {
            return disruptClass;
        } else if (styleClassname.contains("risk")) return riskClass;
        throw new IllegalArgumentException("Style class name not defined");
    }


    public static AnomBarHeader getAnomBarHeaderByUrl(String url) {
        SCPLanguage scpLanguage = SCPLanguage.getByUrl(url);
        String langId = scpLanguage.identifier;
        //nordic branch has multiple languages
        if (langId.equals("nd")) {
            langId = url.substring(url.lastIndexOf('/')+1, url.lastIndexOf(':'));
        }
        return getAnomBarHeader(langId);
    }
    public static AnomBarHeader getAnomBarHeader(String styleClasses) {
        return Arrays.stream(AnomBarHeader.values())
                .filter(anomBarHeader -> styleClasses.contains(anomBarHeader.langIdentifier))
                .findFirst()
                .orElse(AnomBarHeader.ENGLISH);
    }
}
