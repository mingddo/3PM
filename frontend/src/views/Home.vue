<template>
  <div class="home">
    <button class="login-btn"><router-link to="/login" class="login-btn-text">로그인</router-link></button>
    <section class="home-section">
      <h1>오후 세시.</h1>
    </section>
    <section class="home-section">
      <h1>오후 네시.</h1>
    </section>
    <section class="home-section">
      <h1>오후 다섯시.</h1>
    </section>
  </div>
</template>

<script>

export default {
  name: 'Home',
  data () {
    return {
      loginBtn : null,
      loginBtnText : null,
    }
  },
  methods : {
    scrollStatus() {
      let viewportHeight = window.innerHeight;
      let scrollPos = window.scrollY
      if (scrollPos > viewportHeight * 0.05) {
        this.loginBtn.classList.add("login-btn-moved")
      }
      else {
        this.loginBtn.classList.remove("login-btn-moved");
      }
    },
  },
  mounted () {
    this.loginBtn = document.querySelector('.login-btn');
    this.loginBtnText = document.querySelector('.login-btn-text');
    window.addEventListener("scroll",this.scrollStatus);
  },
}
</script>

<style scoped>
  .home-section {
    height: 100vh;
    padding-top: 5vh;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-content: center;
  }

  .login-btn {
    width: 20%;
    position: absolute;
    top: 5vh;
    right: 5%;
    font-weight: bold;
    color: #2c3e50;
    transition: all 0.8s ease-in-out;
    padding : 0.25rem 1rem;
    background: none;
    border: none;
    box-shadow: 0px 5px 10px rgba(0,0,0,0.2);
    border-radius: 30px;
    cursor: pointer;
    font-weight: bold;
    color: #585858;
  }
  .login-btn.active {
    background: #585858;
    color: white;
  }
  .login-btn-moved {
    width: 100%;
    position: fixed;
    font-weight: bold;
    color: #2c3e50;
    transition: all 1s ease-in-out;
    top: 90vh;   
    right: 0; 
    border-radius: 0px;

  }
</style>