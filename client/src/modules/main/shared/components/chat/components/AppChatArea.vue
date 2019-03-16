<template>
    <v-card tile
            max-height="calc(100vh - 90px)"
            height="calc(100vh - 90px)"
            light
            id="chatDisplay">

        <v-layout
                column
                fill-height>

            <v-flex shrink>

                <v-list>

                    <v-list-tile>

                        <v-list-tile-avatar size="40">

                            <img :src="'https://randomuser.me/api/portraits/men/3.jpg'">
                        </v-list-tile-avatar>


                        <v-list-tile-content>

                            <v-list-tile-title>{{ value.name }} {{ value.surname }}</v-list-tile-title>

                            <v-list-tile-sub-title>{{ value.activeStatus }}</v-list-tile-sub-title>
                        </v-list-tile-content>

                        <v-spacer></v-spacer>

                        <v-list-tile-action>

                            <v-btn icon>

                                <v-icon>close</v-icon>
                            </v-btn>
                        </v-list-tile-action>
                    </v-list-tile>
                </v-list>
            </v-flex>

            <v-divider></v-divider>

            <v-flex
                    py-1
                    px-2
                    class="chat-container">

                <v-layout column id="chat-container">

                    <v-layout justify-center>

                        <v-chip small
                                text-color="green accent-3"
                                class="px-4 white font-weight-light">Bugün</v-chip>
                    </v-layout>

                    <app-chat-bubble
                            v-for="message in messages[0]"
                            :key="`chat-item-${message.id}`"
                            :message="message">
                    </app-chat-bubble>
                </v-layout>
            </v-flex>

            <v-divider></v-divider>

            <v-flex shrink>

                <v-list>

                    <v-list-tile>

                        <v-list-tile-content>

                            <v-textarea
                                    v-model="messageValue"
                                    label="Bir mesaj yazın"
                                    solo
                                    single-line
                                    flat
                                    autofocus
                                    class="chat-text-field"
                                    @keydown.enter.exact.prevent
                                    @keyup.enter.exact="send"
                                    @keydown.enter.shift.exact="newline">
                            </v-textarea>
                        </v-list-tile-content>

                        <v-list-tile-action>

                            <v-btn  flat
                                    color="blue"
                                    :disabled="!messageValue"
                                    @click="send">

                                GÖNDER

                                <v-icon right>send</v-icon>
                            </v-btn>
                        </v-list-tile-action>
                    </v-list-tile>
                </v-list>
            </v-flex>
        </v-layout>
    </v-card>
</template>

<script>
    import AppChatBubble from './AppChatBubble'

    export default {
        name: "AppChatArea",
        components: {
            AppChatBubble
        },

        props: {
            value: {
                type: Object,
                default: null
            }
        },

        data () {
            return {
                messageValue: null,

                // status - 0: undelivered, 1: delivered, 2: pending
                messages: [
                    [
                        {
                            id: 0,
                            text: 'Lorem ipsum dolor sit amet',
                            timestamp: '22.04',
                            owner: false,
                            status: 0
                        },
                        {
                            id: 1,
                            text: 'Lorem ipsum dolor sit amet',
                            timestamp: 'Bugün',
                            owner: true,
                            status: 0
                        },
                        {
                            id: 2,
                            text: 'Lorem ipsum dolor sit amet',
                            timestamp: 'Bugün',
                            owner: false,
                            status: 0
                        },
                        {
                            id: 3,
                            text: 'Lorem ipsum dolor sit amet',
                            timestamp: 'Bugün',
                            owner: false,
                            status: 0
                        },
                        {
                            id: 4,
                            text: 'Lorem ipsum dolor sit amet',
                            timestamp: 'Bugün',
                            owner: true,
                            status: 0
                        }
                    ]
                ]
            }
        },

        methods: {
            send: function () {
                const size = this.messages[0].length

                let newMessage = {
                    id: size,
                    text: this.messageValue,
                    timestamp: Date.now(),
                    owner: true,
                    status: 2
                }

                this.messages[0].push(newMessage)
                this.messageValue = null

                this.scrollToBottom()
            },

            scrollToBottom: function () {
                this.$nextTick(() => {
                    let container = this.$el.querySelector(".chat-container");
                    container.scrollTop = container.scrollHeight;
                })
            }
        }
    }
</script>

<style scoped>

    .chat-container {
        overflow-y: scroll !important;
        overflow-x: hidden !important;
    }

    .chat-text-field {
        width: 90% !important;
        max-height: 100% !important;
    }
</style>