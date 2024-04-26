import { createRouter, createWebHistory } from "vue-router";

const routes = [
  {
    path: "/",
    name: "/",
    redirect: "/pres/list",
  },
  {
    path: "/pres/list",
    name: "prescription",
    component: () => import("@/views/PresList.vue")
  },
  {
    path: "/pres/register",
    name: "register",
    component: () => import("@/views/PresRegister.vue")
  },
  {
    path: "/pres/detail",
    name: "detail",
    component: () => import("@/views/PresDetail.vue")
  },
  {
    path: "/signin",
    name: "Signin",
    component: () => import("@/views/Signin.vue")
  },
  {
    path: "/signup",
    name: "Signup",
    component: () => import("@/views/Signup.vue")
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
  linkActiveClass: "active",
});

export default router;
