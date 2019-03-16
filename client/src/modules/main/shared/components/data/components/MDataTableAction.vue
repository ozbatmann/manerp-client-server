<template>
    <v-btn
            depressed
            class="mx-0"
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

            color: {
                type: String,
                default: 'green accent-3'
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