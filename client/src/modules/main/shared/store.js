export default {
    namespaced: true,
    state: {
       loginStatus: false
    },
    actions: {},
    getters: {
        loginStatus: state => {
            return state.loginStatus
        }

    },
    mutations: {
        change(state, value){
            console.log(value)
            state.loginStatus = value
        }
    }
}