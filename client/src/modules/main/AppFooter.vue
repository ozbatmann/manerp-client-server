<template>
    <v-footer
            height="auto"
            color="deep-purple"
            absolute
            style="bottom: 0;"
    >
        <v-layout
                justify-center
                row
                wrap
        >
            <v-flex
                    deep-purple
                    darken-2
                    py-3
                    text-xs-center
                    white--text
                    xs12
            >
                &copy;2019 — <strong>M A N E R P</strong>
            </v-flex>
        </v-layout>
    </v-footer>
</template>

<script>
    export default {
        name: "AppFooter",
        data: () => ({
            links: [
                'Home',
                'About Us',
                'Team',
                'Services',
                'Blog',
                'Contact Us'
            ]
        })
    }
</script>

<style scoped>

</style>
