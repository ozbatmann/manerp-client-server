<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
    <v-card
            :color="backgroundColor"
            elevation="0"
    >
        <v-card-title>
            <v-layout
                    row
                    align-start
            >
                <v-flex>

                    <!-- Overview title -->
                    <div class="headline font-weight-light">{{ title }}</div>

                    <!-- Overview waiting processes -->
                    <div class="max-lines-2">

                        <!-- Overview process size -->
                        <span class="black--text">{{ number }}</span>

                        <!-- Overview process description -->
                        <span class="grey--text"> {{ text }}</span>
                    </div>
                </v-flex>
                <v-flex shrink>

                    <!-- Trending tooltip -->
                    <v-tooltip bottom>

                        <!-- This template activates tooltip on hover -->
                        <template v-slot:activator="{ on }">

                            <!-- Chip object that displays trending percentage -->
                            <v-chip
                                    :color="textColor"
                                    :text-color="textColor"
                                    outline
                                    label
                                    small
                                    class="ml-2"
                                    v-on="on"
                            >
                                <!-- Trending icon -->
                                <v-icon
                                        left
                                        class="mr-0"
                                >
                                    <!-- Trending icon changes according to trending status -->
                                    {{ isTrending ? 'arrow_drop_up' : 'arrow_drop_down' }}
                                </v-icon>

                                <!-- Trending percentage -->
                                %{{ percentage.toFixed(2) }}
                            </v-chip>
                        </template>

                        <!-- Tooltip text -->
                        <span>%{{ percentageTitle }}</span>
                    </v-tooltip>
                </v-flex>
            </v-layout>
        </v-card-title>
        <v-card-text class="pt-0">
            <v-sheet
                    :color="backgroundColor"
                    height="150"
            >
                <!-- Graph tooltip -->
                <!-- Describes what the graph states -->
                <v-tooltip bottom>

                    <!-- Activator template -->
                    <template v-slot:activator="{ on }">

                        <!-- Find a better js lib for reports screen -->
                        <!-- Overview graphics -->
                        <v-sparkline
                                smooth
                                auto-draw
                                :value="statistics"
                                :gradient="['#2196f3']"
                                :gradient-direction="gradientDirection"
                                :type="type"
                                :line-width="lineWidth"
                                :labels="labels"
                                label-size="10"
                                padding="16"
                                height="150"
                                stroke-linecap="round"
                                class="fill-height"
                                v-on="on">
                        </v-sparkline>
                    </template>

                    <!-- Tooltip text -->
                    <span>{{ title }}</span>
                </v-tooltip>
            </v-sheet>
        </v-card-text>
        <v-card-actions>

            <!-- Overview action button -->
            <!-- Redirects to related management page -->
            <v-btn
                    depressed
                    flat
            >
                {{ actionText }}
            </v-btn>
            <v-spacer></v-spacer>

            <!-- Report button tooltip -->
            <v-tooltip bottom>

                <!-- Activator template -->
                <template v-slot:activator="{ on }">

                    <!-- Report action button -->
                    <!-- Redirects to reports page for further detail -->
                    <v-btn
                        flat
                        color="blue"
                        icon
                        v-on="on"
                    >
                        <v-icon>
                            assessment
                        </v-icon>
                    </v-btn>
                </template>

                <!-- Tooltip text -->
                <span>Rapor olarak göster</span>
            </v-tooltip>
        </v-card-actions>
    </v-card>
</template>

<script>
    export default {
        name: "IndexOverviewComponent",
        props: {

            // Title of the overview action button
            actionText: {
                type: String,
                default: null
            },

            // Background color of the overview
            backgroundColor: {
                type: String,
                default: "white"
            },

            // Boolean indicating whether graph
            // is represented in bar mode or line
            bar: {
                type: Boolean,
                default: false
            },

            // Text that will be displayed
            // if there is no waiting process
            emptyText: {
                type: String,
                default: null
            },

            // A Boolean indicating whether
            // overview is in dark mode or not
            dark: {
                type: Boolean,
                default: false
            },

            // A Number that represents
            // count of the waiting processes
            number: {
                type: Number,
                default: 0
            },

            // An Array of the statistics
            // that feeds overview graph
            statistics: {
                type: Array
            },

            // Title of the overview component
            title: {
                type: String,
                default: null
            },

            // Overview process description text
            text: {
                type: String,
                default: null
            }
        },

        data () {
            return {
                // An Array that contains
                // days of week for graph bottom text
                labels: [
                    'P', 'S', 'Ç', 'P', 'C', 'C', 'P'
                ]
            }
        },

        computed: {
            // Last - 1 number in the statistics array
            // will be used to calculate trending status
            prevStatistic () {
                return this.statistics[this.statistics.length - 2]
            },

            // Last number in the statistics array
            // will be used to calculate trending status
            lastStatistic () {
                return this.statistics[this.statistics.length - 1]
            },

            // A Boolean indicating
            // whether numbers are increasing or not
            isTrending () {
                return this.lastStatistic > this.prevStatistic
            },

            // Difference between last and previous statistics
            difference () {
                return this.lastStatistic - this.prevStatistic
            },

            // Representation of the difference as percentage
            percentage () {
                return this.difference / this.lastStatistic * 100
            },

            // Text color of the trending chip
            // Green for trending, red for non-trending
            textColor () {
                return this.isTrending ? '#00E676' : 'red'
            },

            // Type of the graph
            type () {
                return this.bar ? 'bar' : 'trend'
            },

            // Line width of the graph
            // Changes relative to graph type
            lineWidth () {
                return this.bar ? '16' : '8'
            },

            // Colors of the graph
            gradient () {
                return this.isTrending ? ['#00C853', '#00E676'] : ['red', 'red']
            },

            // Gradient direction of the graph
            // Starts from top for bar mode, from left for trend mode
            gradientDirection () {
                return this.bar ? 'top' : 'left'
            },

            // Tooltip title for trending chip
            percentageTitle () {
                return this.isTrending ? this.percentage.toFixed(2) + ' ile yükseliş trendindesiniz!' :
                    this.percentage.toFixed(2) + ' ile düşüş trendindesiniz!'
            }
        }
    }
</script>

<style scoped>
</style>