# Metabolights Live Study Tweeter
PHP based script to tweet details of the study that is released on the present day

## Dependencies

These examples require [tmhOAuth](https://github.com/themattharris/tmhOAuth) version 0.8.0 or higher. They have been
tested with PHP 5.3+.

The easiest way to install tmhOAuth is to use [composer](http://getcomposer.org). if you prefer to do things the manual way
make sure you `git clone` tmhOAuth into the main tmhOAuthExamples directory (the same one this file is in).

## Usage

Edit `tmhOAuthExample.php` and enter your application and user values for:
- 'consumer_key'
- 'consumer_secret'
- 'user_token'
- 'user_secret'
- 'bearer'

If you're just trying things out it's recommended you use a test account instead of your real one as some examples do things
like tweet or follow.

To create an application, visit [dev.twitter.com/apps](https://dev.twitter.com/apps)

For command line, go into the `cli` directory and execute any of the examples using `php <filename>.php`. Remember
some examples make POST actions, and will tweet or follow on your behalf (Metabolights).

## Change History
### 0.1 - 14 June 2013
- switched to Twitter API v1.1
- tweets the study that is released today

## To Do
- Schedule cron job
- Keep calm and tweet