function list_to_tree(list) {
    var map = {}, node, roots = [], i;
    for (i = 0; i < list.length; i += 1) {
        map[list[i].id] = i; // initialize the map
        list[i].children = []; // initialize the children
    }
    for (i = 0; i < list.length; i += 1) {
        node = list[i];
        if (node.parent != null) {
            // if you have dangling branches check that map[node.parentId] exists
            list[map[node.parent]].children.push(node);
        } else {
            roots.push(node);
        }
    }
    for(i = 0; i < roots.length; i+=1){
        roots[i].children.sort((fv,sv) => (fv.orderNo > sv.orderNo) ? 1 : -1)
    }

    roots.sort((fv,sv) => (fv.orderNo > sv.orderNo) ? 1 : -1)
    return roots;
}

export default {
    namespaced: true,
    state: {
        "permList": [],
        "menu": [],
        "user": null,
        "organization": null,
        "auth-token":null
    },
    actions: {},
    getters: {
        get: state => {
            return state
        },
        getStructuredMenu: state => {
            let menu = state["menu"]
            return list_to_tree(menu)
        }
    },
    mutations: {
        SET_ORG(state,value){
            state['organization'] = value
        },
        SET_PERM(state, value) {
            state['permList'].splice(0, state['permList'].length, ...value)
        },
        SET_MENU(state, value) {

            state['menu'].splice(0, state['menu'].length, ...value)
        },
        SET_USERNAME(state, value) {
            state["user"] = value
        },
        SET_TOKEN(state, value) {
            state["user"] = value
        }
    }
}