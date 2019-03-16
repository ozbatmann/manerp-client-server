<template>
    <v-layout :class="{ 'justify-end': message.owner }"
              my-1
              mx-1>

            <div class="bubble body-1 px-3 py-2"
                 :class="bubbleClass">

                {{ message.text }}

                <div class="caption"
                     :class="timestampClass">

                    {{ message.timestamp }}
                </div>
            </div>
    </v-layout>
</template>

<script>
    export default {
        name: "AppChatBubble",

        props: {
            message: Object
        },

        computed: {
            bubbleClass () {
                return this.message.owner ?
                    [ 'blue', 'white--text' ] : [ 'grey lighten-2', 'black--text' ]
            },

            timestampClass () {
                return this.message.owner ?
                    [ 'text-xs-right', 'blue--text', 'text--lighten-4' ] : [ 'grey--text' ]
            }
        }
    }
</script>

<style scoped>

    .bubble {
        border-radius: 10px !important;
        max-width: 40% !important;
    }
</style>