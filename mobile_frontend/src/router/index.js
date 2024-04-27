import { createRouter, createWebHistory } from "vue-router";

const routes= [
  {
    path: "/",
    name: "/",
    redirect: "/qrpass/login"
  },
  {
    path: "/qrpass/login",
    name: "login",
    component: () => import("@/components/QRPassLogin.vue")
  },
  {
    path: "/pc",
    name: "pc",
    component: () => import("@/components/PCConnect.vue")
  },
  {
    path: "/qrpass",
    name: "qrpass",
    component: () => import("@/components/QRPass.vue")
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
  linkActiveClass: "active"
});

export default router;

