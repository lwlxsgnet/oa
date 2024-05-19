const homeChildRoutes = [//home子路由配置
    {
        path: '/welcome',
        name: 'Welcome',
        component: () => import("@/views/welcome.vue"),
        meta: {
            position: []
        }
    },
    {
        path: '/info/dep',
        name: 'InfoDep',
        component: () => import("@/views/info/dep/index.vue"),
        meta: {
            position: ["基础信息", "部门信息"]
        }
    },
    {
        path: '/info/emp',
        name: 'InfoEmp',
        component: () => import("@/views/info/emp/index.vue"),
        meta: {
            position: ["基础信息", "员工信息"]
        }
    },
    {
        path: '/security/role',
        name: 'SecurityRole',
        component: () => import("@/views/security/role/index.vue"),
        meta: {
            position: ["安全管理", "角色信息"]
        }
    },
    {
        path: '/security/user',
        name: 'SecurityUser',
        component: () => import("@/views/security/user/index.vue"),
        meta: {
            position: ["安全管理", "用户信息"]
        }
    },
    {
        path: '/leave/apprev',
        name: 'LeaveApprev',
        component: () => import("@/views/leave/apprev/index.vue"),
        meta: {
            position: ["请假", "请假申请和销假"]
        }
    },
];

const routes = [
    {
        path: '/',
        redirect: '/security/home',
    },
    {
        path: '/test',
        name: 'test',
        component: () => import("@/views/test.vue")
    },
    {
        path: '/security/login',
        name: 'securityLogin',
        component: () => import("@/views/security/login/index.vue")
    },
    {
        path: '/security/home',
        name: 'securityHome',
        component: () => import("@/views/security/home/index.vue"),
        children: homeChildRoutes
    },
];
export default routes;