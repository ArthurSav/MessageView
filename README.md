# MessageView

A simple view that can act as a message view, similar to snackbar.

Allows multiple views to be displayed

<img src="https://media.giphy.com/media/jIpsCJzSYqMsoQm21i/giphy.gif" width="300">


## Usage

```java
List<View> messages = new ArrayList<>();
messages.add(view)
MessageBar messageBar = MessageBar.build(MainActivity.this)
            .addMessages(messages)
            .setBackgroundColor(getResources().getColor(R.color.default_action_color))
            .show();
```

```java
MessageBar.dismiss(MainActivity.this);
```
