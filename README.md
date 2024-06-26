# SCPier - SCP Wiki webscraper

[![](https://jitpack.io/v/doomedcat17/scpier.svg)](https://jitpack.io/#doomedcat17/scpier)

**SCPier is still in development. Check [Status](#status).**

SCPier is Java API for retrieving SCPs, Tales and other content directly from [SCP Foundation Wiki](http://www.scpwiki.com/)

**I don't own any of the provided data**, so please check out [Licensing Guide](https://scp-wiki.wikidot.com/licensing-guide)
if you consider commercial use.  
SCPier itself is free to use for any purpose, if usage does not violate [Wikidot's Terms Of Service](https://www.wikidot.com/legal:terms-of-service).

- [How it works?](#how-it-works)
- [Installation](#installation)
- [Getting data](#getting-data)
  - [ScpWikiDataProvider](#scpwikidataprovider)
  - [ScpWikiData](#scpwikidata)
  - [SCPBranch and SCPLanguage](#scpbranch-and-scplanguage)
- [Content data model](#content-data-model)
  - [Primary types of ContentNodes](#primary-types-of-contentnodes)
    - [TEXT](#text)
    - [HYPERLINK](#hyperlink)
    - [IMAGE, VIDEO, AUDIO](#image-video-audio)
  - [ListNodes](#listnodes)
      - [PARAGRAPH and HEADING](#paragraph-and-heading)
      - [DIV and BLOCKQUOTE](#div-and-blockquote)
      - [TABLE](#table)
      - [LIST_OL, LIST_UL, LIST_DL](#list_ol-list_ul-list_dl)
- [Contribute](#contribute)
  - [Preset](#preset)
    - [WikiElement](#wikielement)
- [Status](#status)

# How it works?

It's basically a webscraper. Wiki content is retrived by [HtmlUnit's](https://htmlunit.sourceforge.io/) webclient.
It runs wiki's javascript (some pages use it) and provides page content.
For more specific cases, pre-defined [Presets](#listnodes) are used for some additional operations before scraping phase. 
Then, retrieved content is scraped by multiple element scrapers and mapped to set of objects.


What is scraped:
  - All text data with its **local** styling
  - Tables
  - Lists
  - Links
  - Images, videos and audios sources ([more info here](#image-video-audio))
  - Tags
  - Last revision time

What is **NOT** scraped:
  - Author's data (planned for future, problems with htmlunit's webclient)
  - Animations and all interactive elements
  - CSS styling (classes, `<style>` elements etc.)
  - Forms and other inputs
  - Scripts
  - Any binary data

**If you want retrieve more specific data form the wiki, check out [scpper](http://scpper.com/)**.
# Installation

SCPier is available as a downloadable .jar java library.
Download [here](https://objects.githubusercontent.com/github-production-release-asset-2e65be/308120373/c3f78dac-3b54-4a9c-b748-46d5353abca8?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAIWNJYAX4CSVEH53A%2F20211104%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20211104T170146Z&X-Amz-Expires=300&X-Amz-Signature=f72e03b0a1d9961c39e5a63e5379723d44df77a14434702835bd39b9d0c26309&X-Amz-SignedHeaders=host&actor_id=0&key_id=0&repo_id=308120373&response-content-disposition=attachment%3B%20filename%3Dscpier-0.5.7.jar&response-content-type=application%2Foctet-stream).

### JitPack
#### Gradle
Add JitPack repository to your project:

```gradle
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
 ```
And then add scpier dependency:
```gradle
dependencies {
  implementation 'com.github.doomedcat17:scpier:0.5.8'
}
 ```
#### Maven
Add JitPack repository to your project:
```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
 ```
And then add scpier dependency:
```xml
<dependency>
  <groupId>com.github.doomedcat17</groupId>
  <artifactId>scpier</artifactId>
  <version>0.5.8</version>
</dependency>
 ```

#### Manual 
Make sure you have [git](https://git-scm.com/) installed on your machine.  
Clone repository:
```
git clone https://github.com/doomedcat17/scpier.git
```
Then enter scpier folder:
```
cd scpier
```
And install using [maven](https://maven.apache.org/):
```
mvn install
```
If you don't have maven installed, you can use maven wrapper instead (it's included in repo):
```
./mvnw install
```

After this, just place the following into your POM's `<dependencies>` section:
```xml
  <dependency>
    <groupId>com.doomedcat17</groupId>
    <artifactId>scpier</artifactId>
    <version>0.5.8</version>
  </dependency>
 ```

# Getting data
### ScpWikiDataProvider

It's a main class for getting content from the wiki. It has three constructors:

```java
ScpWikiDataProvider();
```
Default constructor doesn't accept any parameter. It uses `NicelyLimitedWebClient` which limits **request rate to 240 per minute**.

**Why request rate is limited by default?**

For safety of SCP Wiki servers.

But it has two additional methods providing custom ScpWikiDataProvider, if you don't like the limit.

But keep in mind **it is NOT recommended exceeding defined request rate limit**.

```java
ScpFoundationDataProvider.createWithCustomRequestRateLimit(long timePeriod, long requestCap);
```
It accepts `timePeriod` in seconds and `requestCap`, which defines max number of requests in given time period.

```java
ScpFoundationDataProvider.createWithCustomWebClient(WebClient webClient);
```
It accepts HtmlUnit's `WebClient`, so you can provide your own implementation instead default one.

**ScpWikiDataProvider is not thread safe.** Use one instance per thread.

#### Getting wiki content

To get data from Scp Wiki simply use `getScpWikiContent()` method.

It accepts three parameters:

`articleName` - name of the article.<br>
SCPier puts it in the URL to search for desired article. If you want to get one of the SCPs, you have to provide its full name.
("scp-007" and "SCP-007" will return *SCP-007*).  
Other articles are more complicated case.  
There is no common pattern for article naming in wiki's URLs (RESTful naming).<br>
Some examples:

1. "*PeppersGhost's Proposal, I guess.*" is ```peppersghosts-stupid-proposal```
2. "*Playing God*" is `playing-god`
3. "*Dr Gears' Proposal*" is `dr-gears-s-proposal`
4. "*notgull's Proposal*" is `not-a-seagull-proposal`
5. "*多狼乱な今度の裏切りは撲殺す*" is `3999death`

There are some similarities, but they don't apply to every article.<br>
If you replace all special chars with `-`, it should work for ***most*** cases.

`scpBranch` - `SCPBranch` enum of desired branch.
Defines source branch of desired article.

`scpLanguage` (**Optional***) - `SCPLanguage` enum of desired language.
Defines language of desired article. If not provided, returns article in its original language.  
&ast; required for `NORDIC` branch, because it's multilingual.


```java
ScpWikiDataProvider scpWikiDataProvider = new ScpWikiDataProvider();

ScpWikiData object173 = scpWikiDataProvider
    .getScpWikiContent("SCP-173", ScpBranch.ENGLISH);
```
It returns `ScpWikiData` object with desired article from Scp Wiki in its original form.

```java
ScpWikiData object173 = scpWikiDataProvider
    .getScpWikiContent("SCP-173", ScpBranch.ENGLISH, SCPTranslation.POLISH);
```
Returns article in Polish (if translation is available).

```java
ScpWikiData object173 = scpWikiDataProvider
    .getScpWikiContent("SCP-173", ScpBranch.POLISH);
```
Returns *SCP-PL-173* article in its original form.

#### Exceptions
`SCPierApiException` is the class from which all other exceptions inherit.
If article hasn't been found, the `SCPWikiContentNotFoundException` is thrown.

### ScpWikiData
This object represents data retrieved form wiki. <br>
It has the following variables:
```java
String name;
String title;
SCPBranch branch;
SCPTranslation language;
List<String> tags;
LocalDateTime lastRevisionTimestamp;
String originalSource;
String translationSource;
List<ContentNode<?>> content;
```
`name` - name of the article.

`title` - title of the article from the wiki page.

`scpBranch` - source branch of desired article.

`scpLanguage` - translation language of desired article.

`tags` - list of the article tags.

`lastRevisionTimestamp` - date and time of the last revision (UTC).

`originalSource` - URL of original article.

`translationSource` - URL of translated article.

`content` - content of the article.

Example, SCP-006 as JSON:
```json
{
  "name" : "scp-006",
  "title" : "SCP-006",
  "branch" : "ENGLISH",
  "language" : "ENGLISH",
  "tags" : [ "_cc", "_licensebox", "liquid", "location", "medical", "rewrite", "safe", "scp", "self-repairing" ],
  "lastRevisionDate" : [ 2021, 8, 9, 20, 51, 34 ],
  "originalSource" : "http://www.scp-wiki.wikidot.com/scp-006",
  "translationSource" : null,
  "content" : [ {
    "contentNodeType" : "HEADING",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "Under direct orders of the founder, access is limited to those with Overseer clearance.",
      "styles" : {
        "font-weight" : "bold",
        "font-size" : "1.5em"
      }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "Overseer Clearance Granted",
      "styles" : {
        "font-weight" : "bold"
      }
    } ]
  }, {
    "contentNodeType" : "IMAGE",
    "content" : "https://scp-wiki.wdfiles.com/local--files/scp-006/SCP006_stream-new.jpg",
    "description" : [ {
      "contentNodeType" : "TEXT",
      "content" : "SCP-006",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "Item #:",
      "styles" : {
        "font-weight" : "bold"
      }
    }, {
      "contentNodeType" : "TEXT",
      "content" : " SCP-006",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "Object Class:",
      "styles" : {
        "font-weight" : "bold"
      }
    }, {
      "contentNodeType" : "TEXT",
      "content" : " Safe",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "Special Containment Procedures:",
      "styles" : {
        "font-weight" : "bold"
      }
    }, {
      "contentNodeType" : "TEXT",
      "content" : " Whereas the nature of SCP-006 does not warrant any extensive containment, a certain level of secrecy is necessary regarding the object's existence and properties, for obvious reasons. The following procedures are required not for personnel safety, but to deny or hide knowledge of SCP-006's effects from the personnel who interact with it.",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "1: All personnel interacting with SCP-006 in any physical way are required to wear modified Class VI BNC suits. Before personnel are allowed to perform procedures, they must be briefed with Material SCP-006B or SCP-006C. SCP-006A Briefing is the correct one and is restricted to only those with O5 clearance. To ensure personnel are wearing suits properly, they are to be submerged into a pool of water. Any air bubbles spotted signify a leak in the suit.",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "2: Procedures with SCP-006 are to be carried out under extreme surveillance. In case of contact with SCP-006, the commander in charge will announce Procedure 006-Xi-12, which the personnel have been briefed to believe to mean high toxicity is present and they must evacuate.",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "3: Any procedure in which liquid is acquired from SCP-006 must be approved by three (3) O5 level personnel. The liquid is to be transferred in a Quad-Sealant Container and under armed guard.",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "4: If at any time personnel come into contact with SCP-006 or liquid from SCP-006, they are to be confined and terminated after sufficient studies are done. Due to the nature of SCP-006, the most effective termination method is incineration. (For full report, see file SCP006-TerO5)",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "Description:",
      "styles" : {
        "font-weight" : "bold"
      }
    }, {
      "contentNodeType" : "TEXT",
      "content" : " SCP-006 is a very small spring located 60&nbsp;km west of Astrakhan. Foundation Command was aware of its existence since the 19th century, but were unable to secure it until 1991 due to political reasons. On the spot of the spring, a chemical factory has been constructed as a disguise, with the majority of laborers under Foundation and/or Russian control. The liquid emitted from the spring has been chemically identified as simple mineral water in 1902, but has the unusual property of \"health\".",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "Ingesting the liquid produces the following properties in human beings: the ability to regenerate DNA damaged by sufficient duplication, heightened excitement of cellular duplication, vastly improved abilities in the repair of damaged tissue, and a frightening increase in the effectiveness of the human immune system. Upon testing the liquid on animal subjects, hostile bacteria and viral agents were destroyed immediately. Many reptiles and birds were unaffected, while higher primates experienced the same benefits as humans.",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "HEADING",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "Under direct orders of the founder, access is limited to those with Overseer clearance.",
      "styles" : {
        "font-weight" : "bold",
        "font-size" : "1.5em"
      }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "Overseer Clearance Granted",
      "styles" : {
        "font-weight" : "bold"
      }
    } ]
  }, {
    "contentNodeType" : "IMAGE",
    "content" : "https://scp-wiki.wdfiles.com/local--files/scp-006/SCP006_stream-new.jpg",
    "description" : [ {
      "contentNodeType" : "TEXT",
      "content" : "SCP-006",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "Item #:",
      "styles" : {
        "font-weight" : "bold"
      }
    }, {
      "contentNodeType" : "TEXT",
      "content" : " SCP-006",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "Object Class:",
      "styles" : {
        "font-weight" : "bold"
      }
    }, {
      "contentNodeType" : "TEXT",
      "content" : " Safe",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "Special Containment Procedures:",
      "styles" : {
        "font-weight" : "bold"
      }
    }, {
      "contentNodeType" : "TEXT",
      "content" : " Whereas the nature of SCP-006 does not warrant any extensive containment, a certain level of secrecy is necessary regarding the object's existence and properties, for obvious reasons. The following procedures are required not for personnel safety, but to deny or hide knowledge of SCP-006's effects from the personnel who interact with it.",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "1: All personnel interacting with SCP-006 in any physical way are required to wear modified Class VI BNC suits. Before personnel are allowed to perform procedures, they must be briefed with Material SCP-006B or SCP-006C. SCP-006A Briefing is the correct one and is restricted to only those with O5 clearance. To ensure personnel are wearing suits properly, they are to be submerged into a pool of water. Any air bubbles spotted signify a leak in the suit.",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "2: Procedures with SCP-006 are to be carried out under extreme surveillance. In case of contact with SCP-006, the commander in charge will announce Procedure 006-Xi-12, which the personnel have been briefed to believe to mean high toxicity is present and they must evacuate.",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "3: Any procedure in which liquid is acquired from SCP-006 must be approved by three (3) O5 level personnel. The liquid is to be transferred in a Quad-Sealant Container and under armed guard.",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "4: If at any time personnel come into contact with SCP-006 or liquid from SCP-006, they are to be confined and terminated after sufficient studies are done. Due to the nature of SCP-006, the most effective termination method is incineration. (For full report, see file SCP006-TerO5)",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "Description:",
      "styles" : {
        "font-weight" : "bold"
      }
    }, {
      "contentNodeType" : "TEXT",
      "content" : " SCP-006 is a very small spring located 60&nbsp;km west of Astrakhan. Foundation Command was aware of its existence since the 19th century, but were unable to secure it until 1991 due to political reasons. On the spot of the spring, a chemical factory has been constructed as a disguise, with the majority of laborers under Foundation and/or Russian control. The liquid emitted from the spring has been chemically identified as simple mineral water in 1902, but has the unusual property of \"health\".",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "Ingesting the liquid produces the following properties in human beings: the ability to regenerate DNA damaged by sufficient duplication, heightened excitement of cellular duplication, vastly improved abilities in the repair of damaged tissue, and a frightening increase in the effectiveness of the human immune system. Upon testing the liquid on animal subjects, hostile bacteria and viral agents were destroyed immediately. Many reptiles and birds were unaffected, while higher primates experienced the same benefits as humans.",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "HEADING",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "Under direct orders of the founder, access is limited to those with Overseer clearance.",
      "styles" : {
        "font-weight" : "bold",
        "font-size" : "1.5em"
      }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "Overseer Clearance Granted",
      "styles" : {
        "font-weight" : "bold"
      }
    } ]
  }, {
    "contentNodeType" : "IMAGE",
    "content" : "https://scp-wiki.wdfiles.com/local--files/scp-006/SCP006_stream-new.jpg",
    "description" : [ {
      "contentNodeType" : "TEXT",
      "content" : "SCP-006",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "Item #:",
      "styles" : {
        "font-weight" : "bold"
      }
    }, {
      "contentNodeType" : "TEXT",
      "content" : " SCP-006",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "Object Class:",
      "styles" : {
        "font-weight" : "bold"
      }
    }, {
      "contentNodeType" : "TEXT",
      "content" : " Safe",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "Special Containment Procedures:",
      "styles" : {
        "font-weight" : "bold"
      }
    }, {
      "contentNodeType" : "TEXT",
      "content" : " Whereas the nature of SCP-006 does not warrant any extensive containment, a certain level of secrecy is necessary regarding the object's existence and properties, for obvious reasons. The following procedures are required not for personnel safety, but to deny or hide knowledge of SCP-006's effects from the personnel who interact with it.",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "1: All personnel interacting with SCP-006 in any physical way are required to wear modified Class VI BNC suits. Before personnel are allowed to perform procedures, they must be briefed with Material SCP-006B or SCP-006C. SCP-006A Briefing is the correct one and is restricted to only those with O5 clearance. To ensure personnel are wearing suits properly, they are to be submerged into a pool of water. Any air bubbles spotted signify a leak in the suit.",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "2: Procedures with SCP-006 are to be carried out under extreme surveillance. In case of contact with SCP-006, the commander in charge will announce Procedure 006-Xi-12, which the personnel have been briefed to believe to mean high toxicity is present and they must evacuate.",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "3: Any procedure in which liquid is acquired from SCP-006 must be approved by three (3) O5 level personnel. The liquid is to be transferred in a Quad-Sealant Container and under armed guard.",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "4: If at any time personnel come into contact with SCP-006 or liquid from SCP-006, they are to be confined and terminated after sufficient studies are done. Due to the nature of SCP-006, the most effective termination method is incineration. (For full report, see file SCP006-TerO5)",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "Description:",
      "styles" : {
        "font-weight" : "bold"
      }
    }, {
      "contentNodeType" : "TEXT",
      "content" : " SCP-006 is a very small spring located 60&nbsp;km west of Astrakhan. Foundation Command was aware of its existence since the 19th century, but were unable to secure it until 1991 due to political reasons. On the spot of the spring, a chemical factory has been constructed as a disguise, with the majority of laborers under Foundation and/or Russian control. The liquid emitted from the spring has been chemically identified as simple mineral water in 1902, but has the unusual property of \"health\".",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "Ingesting the liquid produces the following properties in human beings: the ability to regenerate DNA damaged by sufficient duplication, heightened excitement of cellular duplication, vastly improved abilities in the repair of damaged tissue, and a frightening increase in the effectiveness of the human immune system. Upon testing the liquid on animal subjects, hostile bacteria and viral agents were destroyed immediately. Many reptiles and birds were unaffected, while higher primates experienced the same benefits as humans.",
      "styles" : { }
    } ]
  } ]
}
```
### SCPBranch and SCPLanguage
They are enums, which define article's source branch and language.

**All official branches are supported. Translations are made by SCP community, SCPier does not translate anything by itself**.


List of available SCP Wiki branches:

```
ENGLISH, POLISH, RUSSIAN, JAPANESE, CHINESE, CHINESE_TRADITIONAL,
KOREAN, FRENCH, SPANISH, THAI, GERMAN, ITALIAN, UKRAINIAN,
PORTUGUESE, CZECH, GREEK, INDONESIAN, ESTONIAN, TURKISH, VIETNAMESE, 
ARABIAN, HUNGARIAN, ROMANIAN, SLOVENIAN, NORDIC
````

List of available languages:
```
ENGLISH, POLISH, RUSSIAN, JAPANESE, CHINESE, CHINESE_TRADITIONAL,
KOREAN, FRENCH, SPANISH, THAI, GERMAN, ITALIAN, UKRAINIAN,
PORTUGUESE, CZECH, GREEK, INDONESIAN, DANISH, ESTONIAN
FINNISH, NORWEGIAN, SWEDISH, TURKISH, VIETNAMESE, ARABIAN,
HUNGARIAN, ROMANIAN, SLOVENIAN
````

# Content data model

The main class of the data model is generic `ContentNode` class. It has two variables:

```java
ContentNodeType contentNodeType;

T content;
```

`ContentNodeType` is an enum that defines what type of content particular `ContentNode` holds. And content defines
its content, as simple as that.

`ContentNode` has some child classes which have additional variables.

## Primary types of ContentNodes

### TEXT

Defines ContentNode as TextNode. The `content` is of `String` type and it holds a piece of text.<br>
It also has `styles` variable, which is a `Map` of CSS properties and its values applied to the `content`.

**Only local styles are applied!**

```json
{
  "contentNodeType": "TEXT",
  "content": "Bold text",
  "styles": {
    "font-weight": "bold"
  }
}
```

Also, styles can be empty.

```json
{
  "contentNodeType": "TEXT",
  "content": "Plain text",
  "styles": {}
}
```

### HYPERLINK

Defines `ContentNode` as `HyperlinkNode`. It corresponds to the `<a>` HTML element.
It's subclass of `TextNode` with additional `href` variable of `String` type.

```json
{
  "contentNodeType": "HYPERLINK",
  "content": "practical physician",
  "styles": {
    "color": "#901"
  },
  "href": "http://www.scp-wiki.wikidot.com/death-and-the-doctors-hub"
}
```

### IMAGE, VIDEO, AUDIO

Defines `ContentNode` as `EmbedNode` that holds URL of resource as `String`. It also has a variable
named `description`, which is a `List` of TextNodes. It defines a description of given content.

`ImageNode`
```json
{
  "contentNodeType": "IMAGE",
  "content": "http://scp-wiki.wdfiles.com/local--files/scp-009/SCP-009.jpg",
  "description": [
    {
      "contentNodeType": "TEXT",
      "content": "SCP-009 prior to recovery",
      "styles": {}
    }
  ]
}
```

`ImageNode` with styled caption

```json
{
  "contentNodeType": "IMAGE",
  "content": "http://scp-wiki.wdfiles.com/local--files/scp-009/SCP-009.jpg",
  "description": [
    {
      "contentNodeType": "TEXT",
      "content": "SCP-009 ",
      "styles": {
        "color": "red"
      }
    },
    {
      "contentNodeType": "TEXT",
      "content": " prior to recovery",
      "styles": {
        "text-decoration": "underline"
      }
    }
  ]
}
```

`VideoNode`

```json
{
  "contentNodeType": "VIDEO",
  "content": "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
  "description": []
}
```

`AudioNode`

```json
{
  "contentNodeType": "AUDIO",
  "content": "http://www.scp-wiki.net/local--files/scp-049/Addendum0491.mp3",
  "description": []
}
```

## ListNodes

`ListNode` is subclass of `ContentNode` with list of ContentNodes as content.

### PARAGRAPH and HEADING

`PARAGRAPH` defines `ContentNode` as `ParagraphNode`. It's subclass of `ListNode` and corresponds to `<p>` HTML element.
`HEADING` defines `ContentNode` as `HeadingNode` and it's subclass of `ParagraphNode`.
They are identical, but  `HeadingNode` corresponds to ```<h1-h6>``` HTML tags.

***They only hold TextNodes and its subclasses*** (like HyperlinkNodes).

`ParagraphNode`

```json
{
  "contentNodeType": "PARAGRAPH",
  "content": [
    {
      "contentNodeType": "TEXT",
      "content": "This ",
      "styles": {
        "font-weight": "bold"
      }
    },
    {
      "contentNodeType": "TEXT",
      "content": " is ",
      "styles": {}
    },
    {
      "contentNodeType": "TEXT",
      "content": "one ",
      "styles": {}
    },
    {
      "contentNodeType": "TEXT",
      "content": "paragraph.",
      "styles": {
        "text-decoration": "underline"
      }
    }
  ]
}
``` 
`HeadingNode`
```json
{
  "contentNodeType": "HEADING",
  "content": [
    {
      "contentNodeType": "TEXT",
      "content": "Does the black moon howl?",
      "styles": {
        "font-weight": "bold",
        "font-size": "2em"
      }
    }
  ]
}
``` 

### DIV and BLOCKQUOTE

These types are instances of `ListNode`. They correspond to `<div>` and  `<blockquote>` HTML elements.
Usually `<blockquote>` defines some type of note or document on wiki with dashed border and `<div>` is more like
content box with solid border. 

**They can hold all types of ContentNodes.**

`Blockquote`

```json
{
  "contentNodeType": "BLOCKQUOTE",
  "content": [
    {
      "contentNodeType": "HEADING",
      "content": [
        {
          "contentNodeType": "TEXT",
          "content": "Good ",
          "styles": {
            "font-weight": "bold"
          }
        },
        {
          "contentNodeType": "TEXT",
          "content": " Title",
          "styles": {}
        }
      ]
    },
    {
      "contentNodeType": "PARAGRAPH",
      "content": [
        {
          "contentNodeType": "TEXT",
          "content": "Some ",
          "styles": {
            "font-weight": "bold"
          }
        },
        {
          "contentNodeType": "TEXT",
          "content": " stuff",
          "styles": {}
        }
      ]
    }
  ]
}
``` 

`Div`

```json
{
  "contentNodeType": "DIV",
  "content": [
    {
      "contentNodeType": "HEADING",
      "content": [
        {
          "contentNodeType": "TEXT",
          "content": "Check out ",
          "styles": {
            "font-weight": "bold"
          }
        },
        {
          "contentNodeType": "TEXT",
          "content": " this video",
          "styles": {}
        }
      ]
    },
    {
      "contentNodeType": "VIDEO",
      "content": "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
      "caption": []
    }
  ]
}
``` 

### TABLE

Corresponds to `<table>` HTML element. It consists of ListNodes of `TABLE_ROW` type.
And, each `TABLE_ROW` can have multiple ListNodes of `TABLE_CELL`or `TABLE_HEADING_CELL` type.

**```TABLE_CELL``` and ```TABLE_HEADING_CELL``` can hold all types of ContentNodes.**

```json
{
  "contentNodeType": "TABLE",
  "content": [
    {
      "contentNodeType": "TABLE_ROW",
      "content": [
        {
          "contentNodeType": "TABLE_HEADING_CELL",
          "content": [
            {
              "contentNodeType": "PARAGRAPH",
              "content": [
                {
                  "contentNodeType": "TEXT",
                  "content": "Heading",
                  "styles": {}
                }
              ]
            }
          ]
        },
        {
          "contentNodeType": "TABLE_HEADING_CELL",
          "content": [
            {
              "contentNodeType": "PARAGRAPH",
              "content": [
                {
                  "contentNodeType": "TEXT",
                  "content": "Heading",
                  "styles": {}
                }
              ]
            }
          ]
        }
      ]
    },
    {
      "contentNodeType": "TABLE_ROW",
      "content": [
        {
          "contentNodeType": "TABLE_CELL",
          "content": [
            {
              "contentNodeType": "PARAGRAPH",
              "content": [
                {
                  "contentNodeType": "TEXT",
                  "content": "Some table data",
                  "styles": {}
                }
              ]
            }
          ]
        },
        {
          "contentNodeType": "TABLE_CELL",
          "content": [
            {
              "contentNodeType": "PARAGRAPH",
              "content": [
                {
                  "contentNodeType": "TEXT",
                  "content": "And more table data",
                  "styles": {}
                }
              ]
            }
          ]
        }
      ]
    }
  ]
}

``` 

### LIST_OL, LIST_UL, LIST_DL

Corresponds to HTML list elements (`<ol>`, `<ul>` and `<dl>`). Each od them consists of ListNodes of
`LIST_ITEM` type.  
**List items can hold all types of ContentNodes.**

```json
{
  "contentNodeType": "LIST_UL",
  "content": [
    {
      "contentNodeType": "LIST_ITEM",
      "content": [
        {
          "contentNodeType": "PARAGRAPH",
          "content": [
            {
              "contentNodeType": "TEXT",
              "content": "A bed (Denied)",
              "styles": {}
            }
          ]
        }
      ]
    },
    {
      "contentNodeType": "LIST_ITEM",
      "content": [
        {
          "contentNodeType": "PARAGRAPH",
          "content": [
            {
              "contentNodeType": "TEXT",
              "content": "A blanket (Denied)",
              "styles": {}
            }
          ]
        }
      ]
    },
    {
      "contentNodeType": "LIST_ITEM",
      "content": [
        {
          "contentNodeType": "PARAGRAPH",
          "content": [
            {
              "contentNodeType": "TEXT",
              "content": "Books (Denied)",
              "styles": {}
            }
          ]
        }
      ]
    },
    {
      "contentNodeType": "LIST_ITEM",
      "content": [
        {
          "contentNodeType": "PARAGRAPH",
          "content": [
            {
              "contentNodeType": "TEXT",
              "content": "Clothes (Denied)",
              "styles": {}
            }
          ]
        }
      ]
    }
  ]
}
```
# Contribute

Making this project made me realise how creative SCP Community can be. There are many stories and articles with some
type of interaction with the reader, like terminals to operate, buttons to click and fields to fill. 
Obviously SCPier can't handle them by itself, every case is different. So I came with idea of so called "*Presets*". 

**Everybody can write one (basic HTML and CSS knowledge) and it's an easy way to contribute!**

## Preset

`Preset` is a YAML file with instructions for the SCPier how to handle specific cases (like inputs, getting additional content or removing some unwanted content).

*How many articles need their own Preset?*  
I don't really know, but I **assume** it's less than 10 percent of all articles.  

The most problematic case is usage of `code` class. It is mostly used for purely aesthetic purposes, which are not really important for scrapping, though there are times when custom code could possess some storytelling values

`Preset` consists of following properties:  

`articleName`* - name of the article. **RESTFUL! Check [Getting data](#getting-data)**.  

`branch`* - wiki branch of the article.  

`runtime` - number of milliseconds to wait after loading wiki page. Used when script is executed on page load. 

`wikiElements` - list of `WikiElements` to process.  

`removalDefinitions` - [CSS Selectors](https://www.w3schools.com/cssref/css_selectors.asp) of elements to remove. 

`outerContentArticleNames` - list of wiki articles which content should be provided. Given articles contents will be added to the content.

*required properties

**You don't have to provide all properties. Only those that are necessary!**
```yaml
artcileName: "article-name"
scpBranch: "ENGLISH"
runtime: 1000
wikiElements:
  - selector: "#button-to-click"
    elementType: "BUTTON"
    runtime: 1000
  - selector: "#checkbox-to-check"
    elementType: "CHECKBOX"
  - selector: "#radio-to-hmm-check-i-guess?"
    elementType: "RADIO"
  - selector: "#input-to-fill"
    elementType: "INPUT"
    inputValue: "Broken God"
    runtime: 1000
removalDefinitions:
  - "#element-to-remove"
outerContentArticleNames:
  - "article-title-to-provide-content"
```
```yaml
articleName: "scp-3959"
scpBranch: "ENGLISH"
removalDefinitions:
  - "form"
outerContentNames:
  - "scp-3959-restricted"
```
Remove forms and adds "*scp-3959-restricted*" article content

```yaml
articleName: "scp-3211"
scpBranch: "ENGLISH"
wikiElements:
  - selector: "#proceed"
    elementType: "BUTTON"
    runtime: 2000
removalDefinitions:
  - "#footnotes"
```
Remove elements with *footnotes* id, clicks a button with *proceed* id and waits 2000 milliseconds (2 seconds).

### WikiElement

`WikiElement` defines type of element and how to handle it.  
It has the following parameters:  
`selector`* - [CSS Selector](https://www.w3schools.com/cssref/css_selectors.asp) of element.  
`elementType`* - defines type of element  
`runtime` - number of milliseconds to wait after interaction with element. 

*required properties  

**BUTTON**

Element with this type will be clicked. **All elements with onclick attribute are treated as buttons!**

```yaml
selector: "#button-to-click"
elementType: "BUTTON"
```
**RADIO and CHECKBOX**

Element with this type will be checked. Corresponds to `<input>` HTML tag of type `radio` and `checkbox`.

```yaml
selector: "#radio-to-hmm-check-i-guess?"
elementType: "RADIO"
```

```yaml
selector: "#checkbox-to-check"
elementType: "CHECKBOX"
```

**INPUT**  

It has additional property `inputValue` property (required). Element with this type will be filled with given value.

```yaml
selector: "#input-to-fill"
elementType: "INPUT"
inputValue: "Broken God"
runtime: 1000
```

# Status
SCPier is still in development. It can't handle heavily scripted articles and for some of them additional interpretation is needed.
Also, some content can be scraped incorrectly or be missing.
Although the current version is quite stable and safe to use.

If you find any bugs or issues, please open new issues in [Issues](https://github.com/doomedcat17/scpier/issues).

You can also contact me via [Telegram](https://t.me/doomedcat17) or email - doomedcat17@outlook.com.



