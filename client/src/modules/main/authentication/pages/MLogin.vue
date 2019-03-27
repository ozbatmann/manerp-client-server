
<!-- Login component -->
<!-- Handles authentication and login system -->
<template>
    <v-container
            fluid
            fill-height
            pa-0
    >
        <v-layout
                align-center
                fill-height
                wrap
        >
            <v-flex
                    xs7
                    style="position: relative;"
            >
                <div class="m-login__brand">
                    <v-img
                            :src="require('@/assets/logo_w.png')"
                            class="mx-auto m-login__background"
                            height="125"
                            width="200"
                            contain
                    ></v-img>
                    <p class="brand mx-auto">M A N E R P</p>
                </div>
                <div id="m-login__overlay"></div>
                <v-img
                        :src="require('@/assets/login_background.jpeg')"
                        height="100vh"
                ></v-img>
            </v-flex>
            <v-flex
                    xs5
                    px-5
                    style="position: relative;"
            >
                <div class="m-login__clip-path"></div>
                <div class="mb-5">
                    <h4 class="display-1 font-weight-light">
                        Hoşgeldiniz, <br>
                        <span class="green--text text--accent-2">
                            Bumerang Lojistik.
                        </span>
                    </h4>
                    <h2 class="mt-5 font-weight-light">İşinizi uzaya taşıyın 🚀</h2>
                </div>
                <v-text-field
                        v-model="username"
                        solo
                        flat
                        placeholder="Kullanıcı Adı"
                        background-color="grey lighten-4"
                        color="black"
                        required
                ></v-text-field>

                <v-text-field
                        v-model="password"
                        solo
                        flat
                        hide-details
                        placeholder="Şifre"
                        background-color="grey lighten-4"
                        color="black"
                        type="password"
                        required
                ></v-text-field>
                <v-layout pt-3>
                    <v-flex xs4>
                        <v-checkbox
                                hide-details
                                color="green accent-2"
                                label="Beni hatırla"
                                class="m-login__input mt-0"
                        ></v-checkbox>
                        <v-btn
                                depressed
                                color="primary-green"
                                class="white--text mx-0 mt-3"
                                @click="login"
                        >
                            GİRİŞ YAP
                        </v-btn>
                    </v-flex>
                    <v-flex xs8 text-xs-right pt-1>
                        <div style="font-size: 16px;" class="secondary-text">Şifrenizi mi unuttunuz?
                            <span class="primary-green--text">
                                tıklayın.
                            </span>
                        </div>
                    </v-flex>
                </v-layout>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    export default {
        name: "MLogin",

        data () {
            return {
                // An Object that holds
                // username information of the user
                username: null,

                // An Object that holds
                // password information of the user
                password: null
            }
        },

        methods: {
            login () {
                let loginData = {
                    username: this.username,
                    password: this.password
                };

                this.$http.post('v1/auth/signIn', loginData)
                    .then(result => {
                        if(result.data.status === 202){

                            console.log(this.$store)
                             this.$store.commit('shared/change', true)
                            this.$router.push({name:'overview'})
                        }
                        console.log(result)
                    }).catch(error => {
                        console.log(`Error is here: ${error}`)
                })
            }
        }
    }
</script>

<style scoped>
    body {
        background: #f6f9fc;
    }

    .m-login__input label.v-label {
        font-size: 14px !important;
    }

    .m-login__background img {
        -webkit-clip-path: circle(53.7% at 38% 31%);
        clip-path: circle(53.7% at 38% 31%);
    }

    .m-login__brand {
        position: absolute;
        top: 40%;
        left: 50%;
        z-index: 5;
        width: 100%;
        text-align: center;
        -webkit-transform: translate3d(-50%, -40%, 0);
        -moz-transform: translate3d(-50%, -40%, 0);
        -ms-transform: translate3d(-50%, -40%, 0);
        -o-transform: translate3d(-50%, -40%, 0);
        transform: translate3d(-50%, -40%, 0);
    }

    .m-login__brand .brand {
        font-family: 'Century Gothic', sans-serif;
        font-size: 64px;
        width: 75%;
        text-align: justify-all;
        color: white;
    }

    .m-login__brand ~ #m-login__overlay {
        position: absolute;
        width: 100%;
        height: 100%;
        z-index: 3;
        background: rgba(0, 0, 0, 0.5);
    }

    .m-login__clip-path ~ div > h4,
    .m-login__clip-path ~ div > h2 {
        position: relative;
        z-index: 2;
    }

    .m-login__clip-path {
        position: absolute;
        width: 100%;
        height: 100%;
        left: 0;
        top: -45%;
        z-index: 0;
        background: white;
        -webkit-transform: skewY(-12deg);
        -moz-transform: skewY(-12deg);
        -ms-transform: skewY(-12deg);
        -o-transform: skewY(-12deg);
        transform: skewY(-12deg);
    }
</style>
