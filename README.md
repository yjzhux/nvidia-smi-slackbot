# nvidia-smi-slackbot

This slack bot is intended to be used on shared servers where Nvidia GPUs are shared among many people. The bot will notify you when the GPUs are being used, providing the name of the users, the memory used and the process PIDs.

Developed based on [this repo](https://github.com/gariepyalex/nvidia-smi-slackbot)

## Dependency
[Java](https://openjdk.java.net/install/)

[Clojure](https://clojure.org/guides/getting_started)

[Leiningen](https://leiningen.org/#install)

## Slack Setting
Follow the instruction for [sending messages using incoming webhooks](https://api.slack.com/messaging/webhooks) to add an incoming webhook to your slack channel.

## Usage
1. Ensure that nvidia-smi is installed.
2. `cp resources/config.template.clj resources/config.clj`.
3. Edit `resources/config.clj` with the URL of your own slack webhook.
4. `lein run`
5. Keep this program running then you will receive notifications in Slack.

# To-Do
[x] Show names of the machine

[ ] Memory usage / Total memory

[ ] GPU-Util