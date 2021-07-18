# SCPier - SCP Wiki webscrapper

<b>PLEASE KEEP IN MIND IT'S A PROTOTYPE. Check <a href="#status">Status</a>.</b>

Scpier is java library for retriving SCPs and Tales directly form <a href="http://www.scpwiki.com/">SCP Foundation
Wiki</a> (all branches).



I don't own any of the provided data, please check out <a href="https://scp-wiki.wikidot.com/licensing-guide">Licensing Guide</a>
if you consider commercial use.

<ul>
    <li><a href="#how">How it works?</a></li>
    <li>
        <a href="#status">Status</a>
    </li>
    <li>
        <a href="#getting-data">Getting data from SCP Wiki</a>
        <ul>
            <li><a href="#scp-data">SCPData</a></li>
            <li><a href="#scp-enums">SCPBranch and SCPTranslation</a></li>
        </ul>
    </li>
    <li><a href="#model">Content data model</a>
        <ul>
            <li><a href="#primary">Primary types of ContentNodes</a>
                <ul>
                    <li><a href="#text">TEXT</a></li>
                    <li><a href="#hyperlink">HYPERLINK</a></li>
                    <li><a href="#embedcontent">IMAGE, VIDEO, AUDIO</a></li>
                </ul>
            </li>
            <li><a href="#listNodes">ListNodes</a>
                <ul>
                    <li><a href="#paragraph">PARAGRAPH</a></li>
                    <li><a href="#block">DIV, BLOCKQUOTE</a></li>
                    <li><a href="#table">TABLE</a></li>
                    <li><a href="#lists">LIST_OL, LIST_UL, LIST_DL</a></li>
                </ul>
            </li>
        </ul>
    </li>
</ul>

<h1 id="status">Status</h1>
SCPier is in it's early development stages. It can't handle heavily scripted articles and for some of them additional interpretation is needed.
Also some content can be scrapped incorrectly or be missing.<br>

If you meet any bugs or issues, please open new issues in <a href="https://github.com/doomedcat17/scpier/issues">Issues</a> section.<br>
You can also contact me via <a href="https://t.me/doomedcat17">Telegram</a>.


<h1 id="how">How it works?</h1>

It's basically webscraper. HTML elements are retrieved using <a href="https://jsoup.org/">jsoup</a>. If particular SCP
or Tale uses JavaScript, then the script is run by <a href="https://htmlunit.sourceforge.io/">HtmlUnit</a>. Retrieved
HTML elements are scrapped and interpreted by multiple ```ElementScrapper``` classes.

<h1 id="getting-data">Getting Data from SCP Wiki</h1>

Do get data from Scp Wiki simply create instance of ```ScpWikiDataProvider``` class and call ```getScpWikiContent()``` method.<br>
It has three parameters:<br>

<span style="font-family: monospace; font-weight: bold">articleName</span> - name of the article.<br>
SCPier puts it in the URL to search for desired article. If you want to get one of the SCPs, you can only put
its id ignoring heading zeros.
(<i>Example: "7", "07" "007", "scp-007" and "SCP-007" will return SCP-007</i>).<br>
Non-SCPs articles are more complicated case.<br>
There is no common pattern for article naming in wiki's URLs (RESTful naming).<br>
Some examples:<br>
1. "<i>PeppersGhost's Proposal, I guess.</i>" is ```peppersghosts-stupid-proposal```
2. "<i>Playing God</i>" is ```playing-god```
3. "<i>Dr Gears' Proposal</i>" is ```dr-gears-s-proposal```
4. "<i>notgull's Proposal</i>" is ```not-a-seagull-proposal```
5. "<i>多狼乱な今度の裏切りは撲殺す</i>" is ```3999death```

There are some similarities, but they don't apply to every article.<br>
But if you replace all special chars with '-', it will work for <i>most</i> cases.

<span style="font-family: monospace; font-weight: bold">scpBranch</span> - ```SCPBranch``` enum of desired branch.
Defines source branch of desired article.<br>

<span style="font-family: monospace; font-weight: bold">scpTranslation</span> - ```SCPTranslation``` enum of desired translation.
Defines translation of desired article.<br>




```
ScpWikiDataProvider scpWikiDataProvider = new ScpWikiDataProvider();

ScpWikiData object173 = scpWikiDataProvider
    .getScpWikiContent("173", ScpBranch.ENGLISH, ScpTranslation.ORIGINAL);
```
It returns ```ScpWikiData``` object with desired article from Scp Wiki.<br>
<h3 id="scp-data">ScpWikiData</h3>
This object represents data retrieved form wiki. <br>
It has the following variables
```
String title;

List<ContentNode<?>> content;

List<String> tags;

String source;
```
```title``` - title of the article from wiki.<br>
```content``` - content of the article. <br>
```tags``` - list of the article tags.<br>
```source``` - link of selected article.
```
{
  "title" : "SCP-006",
  "content" : [ {
    "contentNodeType" : "IMAGE",
    "content" : "http://scp-wiki.wdfiles.com/local--files/scp-006/SCP006_stream-new.jpg",
    "description" : [ {
      "contentNodeType" : "TEXT",
      "content" : "SCP-006",
      "styles" : { }
    } ]
  }, {
    "contentNodeType" : "HEADING",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "Under direct orders of the founder, access is limited to those with Overseer clearance.",
      "styles" : {
        "font-size" : "1.5em",
        "font-weight" : "bold"
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
  } ],
  "tags" : [ "_cc", "_licensebox", "liquid", "location", "medical", "safe", "scp", "self-repairing" ],
  "source" : "http://www.scp-wiki.wikidot.com/scp-006"
}
```
<h3 id="scp-enums">SCPBranch and SCPTranslation</h3>
They are enums, which define source branch and desired translation.
<p style="font-weight: bold;">All branches are available with all translations made by community.</p>
<p style="font-weight: bold;">SCPier does not transtale anything by itself!</p>
List of SCP Wiki branches and translations:

```
ENGLISH, POLISH, RUSSIAN, JAPANESE, CHINESE, KOREAN,
FRENCH, SPANISH, THAI, GERMAN, ITALIAN, UKRAINIAN,
PORTUGUESE, CZECH, GREEK, INDONESIAN, DANISH,
FINNISH, NORWEGIAN, SWEDISH, TURKISH, VIETNAMESE
````

There is one more translation called ```ORIGINAL``` and it is used for getting article in branch's native language.

```
scpWikiDataProvider
    .getScpWikiContent("173", ScpBranch.ENGLISH, ScpTranslation.ORIGINAL);
```



<h1 id="model">Content data model</h1>

The main class of the data model is generic ```ContentNode``` class. It has two variables:

```
ContentNodeType contentNodeType;

T content;
```

```ContentNodeType``` is an enum that defines what type of content particular ```ContentNode``` holds. And content is
content, as simple as that.

```ContentNode``` has some child classes which have additional variables.

<h2 id="primary">Primary types of ContentNodes</h2>

<h3 id="text">TEXT</h3>

Defines ContentNode as TextNode. The ```content``` is of ```String``` type and it holds a piece of text.<br>
It also has ```styles``` variable. It's a ```Map``` of CSS properties and its values applied to the ```content```.

<b style="color: orange;">Only local HTML styles are applied!</b>

```
{
  "contentNodeType": "TEXT",
  "content": "Bold text",
  "styles": {
    "font-weight": "bold"
  }
}
```

Also, styles can be empty.

```
{
  "contentNodeType": "TEXT",
  "content": "Plain text",
  "styles": {}
}
```

<h3 id="hyperlink">HYPERLINK</h3>

Defines ```ContentNode``` as ```HyperlinkNode```. It corresponds to the ```<a>``` HTML tag.<br>It's subclass
of ```TextNode``` with additional ```href``` variable of ```String``` type.

```
{
  "contentNodeType": "HYPERLINK",
  "content": "practical physician",
  "styles": {
    "color": "#901"
  },
  "href": "http://www.scp-wiki.wikidot.com/death-and-the-doctors-hub"
}
```

<h3 id="embedcontent">IMAGE, VIDEO, AUDIO</h3>

Defines ```ContentNode``` as ```EmbedNode``` that holds URL of resource as ```String```.<br> It also has a variable
named ```caption```, which is a ``List`` of TextNodes. It defines a description of given content. The description mainly
applies for images.

ImageNode

```
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

ImageNode with styled caption

```
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

VideoNode

```
{
  "contentNodeType": "VIDEO",
  "content": "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
  "description": []
}
```

AudioNode

```
{
  "contentNodeType": "AUDIO",
  "content": "http://www.scp-wiki.net/local--files/scp-049/Addendum0491.mp3",
  "description": []
}
```

<h2 id="listNodes">ListNodes</h2>

```ListNode``` is subclass of ```ContentNode``` with ```List``` of ContentNodes as ```content```.

<h3 id="paragraph">PARAGRAPH and HEADING</h3>

```PARAGRAPH``` defines ```ContentNode``` as ```ParagraphNode```. It's subclass of ```ListNode``` and corresponds to ```<p>``` HTML
tag.<br>
```HEADING``` defines ```ContentNode``` as ```HeadingNode``` and it's subclass of ```ParagraphNode```.
They are identical, but  ```HeadingNode``` corresponds to ```<h1-h6>``` HTML tags.

They <b>only</b> holds TextNodes and its subclasses (like HyperlinkNodes).

Paragraph

```
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
Heading
```
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

<h3 id="block">DIV and BLOCKQUOTE</h3>

These types are instances of ```ListNode```. They corresponds to ```<div>``` and  ```<blockquote>``` HTML tags.
Usually ```<blockquote>``` defines some type of note or document on wiki with dashed border and ```<div>``` is more like
content box with solid border. <b>They can hold all types of ContentNodes.</b>

Blockquote

```
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

Div

```
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

<h3 id="table">TABLE</h3>

Corresponds to ```<table>``` HTML tag.<br> It consists of ListNodes of ```TABLE_ROW``` type (```<tr>``` HTML tag).<br>
Each ```TABLE_ROW``` can have multiple ListNodes of ```TABLE_CELL``` (```<td>``` HTML tag)
or ```TABLE_HEADING_CELL``` (```<th>``` HTML tag)
type. <br>
<b>```TABLE_CELL``` and ```TABLE_HEADING_CELL``` can hold all types of ContentNodes.<br></b>

```
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

<h3 id="lists">LIST_OL, LIST_UL, LIST_DL</h3>

Corresponds to HTML list tags (```<ol>```, ```<ul>``` and ```<dl>```). Each od them consists of ListNodes of
```LIST_ITEM``` type (```<li>``` HTML tag).<br>
<b>They can hold all types of ContentNodes.</b>

```
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


