<template>
  <div>
    <div class="container">
      <p>카카오 계정으로 간편 로그인</p>
      <div class="login-input">
        <input v-model="email" type="text" placeholder="카카오메일 아이디, 이메일, 전화번호">
      </div>
      <div class="login-input">
        <input v-model="password" @keyup.enter="login" type="password" placeholder="비밀번호">
      </div>
      <div class="login-input">
        <button @click="login">로그인</button>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'Login',
  data () {
    return {
      email : '',
      password : '',
    }
  },
  methods : {
    login() {
      alert(`EMAIL : ${this.email}\nPASSWORD : ${this.password}`);
      this.$router.push({name : "Signup"})
    }
  },
};
</script>

<style scoped>
  .container {
    height: 100vh;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
  .container p {
    font-size: 1.5rem;
    font-weight: bold;
  }
  ::placeholder {
    
    color: rgba(0, 0, 0, 0.5)
  }
  .login-input {
    margin-bottom: 1rem;
    width: 80%;
  }
  .login-input input {
    padding-left: 10px;
    width: 100%;
    height: 50px;
    color: #000;
    border: 1px solid #000;
    border-radius: 5px;
  }
  .login-input button {
    width: 100%;
    height: 50px;
    padding : 0.25rem 1rem;
    background: none;
    border: none;
    box-shadow: 0px 5px 10px rgba(0,0,0,0.2);
    border-radius: 30px;
    cursor: pointer;
    font-weight: bold;
    color: #585858;
  }
</style>
