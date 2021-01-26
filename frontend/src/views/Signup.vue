<template>
  <div>
    <div class="container">
      <p>마지막으로 사용할 닉네임을 입력해주세요.</p>
      <div class="signup-input">
        <input v-model="nickname" type="text" placeholder="영어/한글/숫자 4자 이상 10자 이내로 입력">
        <button 
        @click="checkOverlap"
        :class="{disabledBtn:!isPossibleName}">중복확인</button>
      </div>
      <div class="signup-input">
        <button 
        @click="signup"
        :disabled="isOverlaped"
        :class="{disabledBtn:isOverlaped}">회원가입</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Signup',
  data () {
    return {
      nickname : '',
      isPossibleName : false,
      isOverlaped : true,
    }
  },
  watch : {
    nickname : function() {
      this.nicknameValidate();
    },
  },
  methods : {
    signup() {
      alert(`NICKNAME : ${this.nickname}`)
    },
    nicknameValidate() {
      const nickname = this.nickname;
      const nicknamePattern = /^[a-zA-Z0-9가-힣]{4,10}$/;
      const patternCheck = nicknamePattern.test(nickname);
      if (nickname.length >= 10 || !patternCheck) {
        this.isPossibleName = false;
      }
      else {
        this.isPossibleName = true;
      }
    },
    checkOverlap() {
      // DB에 중복된 닉네임이 있는지 확인하여 회원가입 버튼 활성화
      alert('사용 가능한 아이디입니다')
      this.isOverlaped = false
    },
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
  .signup-input {
    margin-bottom: 1rem;
    width: 80%;
  }
  .signup-input input {
    padding-left: 10px;
    width: 100%;
    height: 50px;
    color: #000;
    border: 1px solid #000;
    border-radius: 5px;
    margin-bottom: 1rem;
  }
  .signup-input button {
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
  .disabledBtn {
    opacity: .2;
  }
</style>

