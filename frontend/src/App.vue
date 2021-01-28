<template>
  <div>
    <Nav />
    <router-view :key="$route.fullPath" />
  </div>
</template>

<script>
import {mapState} from 'vuex'
import Nav from './components/Common/Nav.vue'

export default {
  components: { Nav },
  methods: {
    goToPersonal () {
      this.$router.push({name: 'NewsfeedPersonal', query: { Category: '꽃보다 집'}})
    },
    goToProfile () {
      this.$router.push({name: 'MyPage'})
    },
  },
  computed : {
    ...mapState(['isLoginUser'])
  },
}

</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  /* display: flex;
  justify-content: center; */
}

/* #nav {
  width: 100%;
  position: fixed;
  top: 5vh;
  left: 5%;
  z-index: 1000;
  height: 60px;
} */

/* #nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: black;
} */

  button a {
    text-decoration: none;
  }
  button a:visited {
    color : black;
  }
  button {
    margin : 2px;
    padding : 0.25rem 0.5rem;
    background: none;
    border: none;
    box-shadow: 0px 5px 10px rgba(0,0,0,0.2);
    border-radius: 30px;
    cursor: pointer;
    font-weight: bold;
    color: #585858;
  }
  button.active {
    background: #585858;
    color: white;
  }
  /* .nav-menu-wrapper {
    display: inline ;
  } */
</style>
