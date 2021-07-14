# SCPier
PLEASE KEEP IN MIND IT'S A PROTOTYPE

Scpier is java library for retriving SCPs and Tales directly form SCP Foundation Wiki (all branches) as JSON.

## How it works?
It's basically webscraper. HTML elements are retrived using ```Jsoup```. If pariticular SCP or Tale uses JavaScript than the sript is run by ```HtmlUnit```.
Retrived HTML elements are scrapped and interpreted by multiple ElementScrapper classes. 

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
### ContentNodeType.TEXT
Defines ContentNode as TextNode.
The ```content``` is of ```String``` type and it holds a piece of text.<br>
It also has ```styles``` variable. It's a ```Map``` of CSS rules and properties applied to the ```content```.
#### Only local styles are applied!
Example:
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
Example:
```
}
    "contentNodeType" : "TEXT",
    "content" : "Plain text",
    "styles" : {
      "font-weight" : {}
}
```
### ContentNodeType.PARAGRAPH
Corresponds to ```<p>``` HTML tag. 
The content is a ```List``` of TextNodes
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


