# SCPier - SCP Wiki webscrapper

PLEASE KEEP IN MIND IT'S A PROTOTYPE

Scpier is java library for retriving SCPs and Tales directly form <a href="http://www.scpwiki.com/">SCP Foundation
Wiki</a> (all branches) as JSON.

<ul>
    <li><a href="#how">How it works?</a></li>
    <li><a href="#model">Data Model</a>
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
    <li>
        <a id="#getting-data">Getting data from SCP Wiki</a>
        <ul>
            <li>
                <a>SCPFoundationDataProvider</a>
                <ul>
                    <li><a href="#paragraph">SCPData</a></li>
                    <li><a href="#block">SCPBranch and SCPTranslation</a></li>
                </ul>
            </li>
        </ul>
    </li>
    <li>
        <a>Status</a>
    </li>
</ul>

<h1 id="how">How it works?</h1>

It's basically webscraper. HTML elements are retrieved using <a href="https://jsoup.org/">jsoup</a>. If particular SCP
or Tale uses JavaScript, then the script is run by <a href="https://htmlunit.sourceforge.io/">HtmlUnit</a>. Retrieved
HTML elements are scrapped and interpreted by multiple ```ElementScrapper``` classes.

<h1 id="model">Data model</h1>

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

<h3 id="paragraph">PARAGRAPH</h3>

Defines ```ContentNode``` as ```ParagraphNode```. It's subclass of ```ListNode``` and corresponds to ```<p>``` HTML
tag.<br>
It only holds TextNodes and its subclasses (like HyperlinkNodes). Example:

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

<h3 id="block">DIV, BLOCKQUOTE</h3>

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
<b>They can hold all types of ContentNodes.<b>

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

<h1 id="getting-data">Getting Data from SCP Wiki</h1>



