const routes = [
    {
        path: '/test',
        name: 'test',
        component: ()=> import("@/views/test.vue")
    },
    {
        path: '/security/login',
        name: 'securityLogin',
        component: ()=> import("@/views/security/login/index.vue")
    },
    {
        path: '/security/home',
        name: 'securityHome',
        component: ()=> import("@/views/security/home/index.vue")
    }
    
];
export default routes;