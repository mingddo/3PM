import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Signup from '../views/Signup.vue'
import Login from '../views/Login.vue'
import NewsfeedPersonal from '../views/NewsFeed/NewsfeedPersonal.vue'
import NewsfeedDetail from '../views/NewsFeed/NewsfeedDetail.vue'
import NewsfeedForm from '../views/NewsFeed/NewsfeedForm.vue'
import MyPage from '../views/MyPage.vue'
import MyPageEdit from '../views/MyPageEdit.vue'
import Search from '../views/Search.vue'
import GroupDetail from '../views/GroupFeed/GroupDetail.vue'
import GroupPage from '../views/GroupFeed/GroupPage.vue'
import GroupNewsFeed from '../views/GroupFeed/GroupNewsFeed.vue'
import GroupNewsFeedDetail from '../views/GroupFeed/GroupNewsFeedDetail.vue'
import GroupNewsFeedForm from '../views/GroupFeed/GroupNewsFeedForm.vue'
import GroupForm from '../views/GroupFeed/GroupForm.vue'


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/mypage',
    name: 'MyPage',
    component: MyPage
  },
  {
    path: '/mypage-edit',
    name: 'MyPageEdit',
    component: MyPageEdit
  },
  {
    path: '/newsfeed-personal',
    name: 'NewsfeedPersonal',
    component: NewsfeedPersonal
  },
  {
    path: '/newsfeed-detail',
    name: 'NewsfeedDetail',
    component: NewsfeedDetail
  },
  {
    path: '/newsfeed-form',
    name: 'NewsfeedForm',
    component: NewsfeedForm
  },
  {
    path: '/search',
    name: 'Search',
    component: Search
  },
  {
    path: '/groupdetail',
    name: 'groupdetail',
    component: GroupDetail
  },
  {
    path: '/grouppage',
    name: 'grouppage',
    component: GroupPage
  },
  {
    path: '/groupnewsfeed',
    name: 'groupnewsfeed',
    component: GroupNewsFeed
  },
  {
    path: '/groupnewsfeeddetail',
    name: 'groupnewsfeeddetail',
    component: GroupNewsFeedDetail
  },
  {
    path: '/groupnewsfeed-form',
    name: 'groupnewsfeedform',
    component: GroupNewsFeedForm
  },
  {
    path: '/groupform',
    name: 'groupform',
    component: GroupForm
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
