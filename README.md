# SCPier - SCP Wiki webscrapper
PLEASE KEEP IN MIND IT'S A PROTOTYPE

Scpier is java library for retriving SCPs and Tales directly form SCP Foundation Wiki (all branches) as JSON.

## How it works?
It's basically webscraper. HTML elements are retrived using ```Jsoup```. If pariticular SCP or Tale uses JavaScript than the script is run by ```HtmlUnit```.
Retrived HTML elements are scrapped and interpreted by multiple ```ElementScrapper``` classes. 

To get content from SCP Wiki, you have to initialize ScpFundationDataProvider class:

```
ScpFoundationDataProvider scpFoundationDataProvider = new ScpFoundationDataProvider();
```

Now, let's take a look at data model

## Data model

The main class of the data model is generic ContentNode class. 
It has two variables:
```
ContentNodeType contentNodeType;

T content;
```
```ContentNodeType``` is an enum that defines what type of content particular ```ContentNode``` holds.
And content is content, as simple as that.

```ContentNode``` has some child classes which have additional variables.

### Primary types of ContentNode
#### ContentNodeType.TEXT
Defines ContentNode as TextNode.
The ```content``` is of ```String``` type and it holds a piece of text.<br>
It also has ```styles``` variable. It's a ```Map``` of CSS properties and its values applied to the ```content```.
#### Only local styles are applied!
```
}
    "contentNodeType" : "TEXT",
    "content" : "Bold text",
    "styles" : {
      "font-weight" : "bold"
      }
}
```
Also styles can be empty.
```
}
    "contentNodeType" : "TEXT",
    "content" : "Plain text",
    "styles" : {
      "font-weight" : {}
}
```
#### ContentNodeType.HYPERLINK
Defines ContentNode as HyperlinkNode. It corresponds to the ```<a>``` HTML tag.
It's subclass of TextNode with additonal ```href``` variable of String type. 
```
}
    "contentNodeType" : "HYPERLINK",
      "content" : "practical physician",
      "styles" : {
        "color" : "#901"
      },
      "href" : "http://www.scp-wiki.wikidot.com/death-and-the-doctors-hub"
}
```
#### ContentNodeType.IMAGE, ContentNodeType.VIDEO, ContentNodeType.AUDIO
Defines ContentNode as EmbedNode that holds URL of resource as String.
It also has a variable named ```caption```, which is a ``List`` of TextNodes. It defines a caption (most of images has captions).
Caption can be empty but not null.

ImageNode:
```
{
    "contentNodeType" : "IMAGE",
    "content" : "http://scp-wiki.wdfiles.com/local--files/scp-009/SCP-009.jpg",
    "caption" : [ {
      "contentNodeType" : "TEXT",
      "content" : "SCP-009 prior to recovery",
      "styles" : { }
    } ]
}
```
ImageNode with styled caption:
```
{
    "contentNodeType" : "IMAGE",
    "content" : "http://scp-wiki.wdfiles.com/local--files/scp-009/SCP-009.jpg",
    "caption" : [ {
      "contentNodeType" : "TEXT",
      "content" : "SCP-009 ",
      "styles" : {
        "color" : "red"
      }
    },
    {
      "contentNodeType" : "TEXT",
      "content" : " prior to recovery",
      "styles" : {
        "text-decoration" : "underline"}
    }]
}
```
ImageNode:
```
{
    "contentNodeType" : "IMAGE",
    "content" : "http://scp-wiki.wdfiles.com/local--files/scp-009/SCP-009.jpg",
    "caption" : [ {
      "contentNodeType" : "TEXT",
      "content" : "SCP-009 prior to recovery",
      "styles" : { }
    } ]
}
```
VideoNode:
```
{
    "contentNodeType" : "VIDEO",
    "content" : "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
    "caption" : []
}
```
AudioNode:
```
{
    "contentNodeType" : "AUDIO",
    "content" : "http://www.scp-wiki.net/local--files/scp-049/Addendum0491.mp3",
    "caption" : []
}
```
### ListNode

ListNode is subclass of ContentNode with List of ContentNodes as content.

#### ContentNodeType.PARAGRAPH
Defines ContentNode ParagraphNode.
It's subclass of ListNode and corresponds to ```<p>``` HTML tag. 
It only holds TextNodes and its subclasses (like HyperlinkNodes).
Example:
```
{
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "This ",
      "styles" : {
        "font-weight" : "bold"
      }
    }, {
      "contentNodeType" : "TEXT",
      "content" : " is ",
      "styles" : { }
    },
    {
      "contentNodeType" : "TEXT",
      "content" : "one ",
      "styles" : { }
    },
    {
      "contentNodeType" : "TEXT",
      "content" : "paragraph.",
      "styles" : {
        "text-decoration" : "underline"}
    }]
  }
``` 
#### ContentNodeType.DIV, ContentNodeType.BLOCKQUOTE
These types are instances of ListNode. They corresponds to ```<div>``` and  ```<blockquote>``` HTML tags.
Usually ```<blockquote>``` defines some type of note or document on wiki with dashed border and ```<div>``` is more like content box with solid border.
They can hold all types of ContentNodes.
Example:
```
{
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "This ",
      "styles" : {
        "font-weight" : "bold"
      }
    }, {
      "contentNodeType" : "TEXT",
      "content" : " is ",
      "styles" : { }
    },
    {
      "contentNodeType" : "TEXT",
      "content" : "one ",
      "styles" : { }
    },
    {
      "contentNodeType" : "TEXT",
      "content" : "paragraph.",
      "styles" : {
        "text-decoration" : "underline"}
    }]
  }
``` 

#### ContentNodeType.TABLE
Corresponds to ```<table>``` HTML tag. It consists of ListNodes of TABLE_ROW (```<tr>``` HTML tag) type.<br>
Each TABLE_ROW has multiple ListNodes of TABLE_CELL (```<td>``` HTML tag) or TABLE_HEADING_CELL (```<th>``` HTML tag) type. <br>
TABLE_CELL and TABLE_HEADING_CELL can hold all types of ContentNodes.
Example:
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
#### ContentNodeType.LIST_OL, ContentNodeType.LIST_UL, ContentNodeType.LIST_DL, 
Corresponds to HTML list tags (```<ol>```, ```<ul>``` and ```<dl>```). Each od them consists of ListNodes of LIST_ITEM (```<li>``` HTML tag) type.<br>
They can hold all types of ContentNodes.
Example:
```
{
  "contentNodeType" : "LIST_UL",
    "content" : [ {
      "contentNodeType" : "LIST_ITEM",
      "content" : [ {
        "contentNodeType" : "PARAGRAPH",
        "content" : [ {
          "contentNodeType" : "TEXT",
          "content" : "A bed (Denied)",
          "styles" : { }
        } ]
      } ]
    }, {
      "contentNodeType" : "LIST_ITEM",
      "content" : [ {
        "contentNodeType" : "PARAGRAPH",
        "content" : [ {
          "contentNodeType" : "TEXT",
          "content" : "A blanket (Denied)",
          "styles" : { }
        } ]
      } ]
    }, {
      "contentNodeType" : "LIST_ITEM",
      "content" : [ {
        "contentNodeType" : "PARAGRAPH",
        "content" : [ {
          "contentNodeType" : "TEXT",
          "content" : "Books (Denied)",
          "styles" : { }
        } ]
      } ]
    }, {
      "contentNodeType" : "LIST_ITEM",
      "content" : [ {
        "contentNodeType" : "PARAGRAPH",
        "content" : [ {
          "contentNodeType" : "TEXT",
          "content" : "Clothes (Denied)",
          "styles" : { }
        } ]
      } ]
    } ]
}

``` 

