<template>
  <div id="app">
    <div id="nav" v-if="true">
      <button><router-link to="/">오후 세시</router-link></button>
      <!-- <button><router-link to="/">프로필</router-link></button>
      <button><router-link to="/">검색아이콘</router-link></button> -->
    </div>
    <router-view :key="$route.fullPath" />
  </div>
</template>

<script>
export default {};
</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  display: flex;
  justify-content: center;
}

#nav {
  position: fixed;
  top: 5vh;
  left: 5%;
  z-index: 1000;
  height: 60px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: black;
}

button a {
  text-decoration: none;
}
button a:visited {
  color: black;
}
button {
  padding: 0.25rem 1rem;
  background: none;
  border: none;
  box-shadow: 0px 5px 10px rgba(0, 0, 0, 0.2);
  border-radius: 30px;
  cursor: pointer;
  font-weight: bold;
  color: #585858;
}
button.active {
  background: #585858;
  color: white;
}
</style>
