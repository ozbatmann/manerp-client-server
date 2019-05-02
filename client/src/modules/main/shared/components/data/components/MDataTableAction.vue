<template>
    <v-btn
            depressed
            :disabled="disabled"
            class="mx-0"
            :class="[background, `${textColor}--text`]"
            @click="onClick"
            @hover="onHover"
    >
        <v-icon
                left
                size="20"
                :color="color"
        >
            {{ icon }}
        </v-icon>
        {{ title }}
    </v-btn>
</template>

<script>
    export default {
        name: "MDataTableAction",

        props: {
            title: {
                type: String,
                default: null
            },

            icon: {
                type: String,
                default: 'add'
            },

            background: {
                type: String,
                default: 'deep-purple'
            },

            color: {
                type: String,
                default: 'green accent-3'
            },

            disabled: {
                type: Boolean,
                default: false
            },

            textColor: {
                type: String,
                default: 'white'
            }
        },

        methods: {
            onClick: function () {
                this.$emit('click')
            },

            onHover () {
                this.$emit('hover')
            }
        }
    }
</script>

<style scoped>

</style>
