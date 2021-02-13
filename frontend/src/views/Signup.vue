<template>
  <div>
    <div class="container">
      <p>마지막으로 사용할 닉네임을 입력해주세요.</p>
      <div class="signup-input">
        <input v-model="nickname" type="text" placeholder="영어/한글/숫자 4자 이상 10자 이내로 입력">
        <button 
        @click="checkOverlap"
        :disabled="!isPossibleName"
        :class="{disabledBtn:!isPossibleName}">중복확인</button>
      </div>
      <div class="signup-input">
        <button 
        @click="onClickSignup"
        :disabled="isOverlapped"
        :class="{disabledBtn:isOverlapped}">회원가입</button>
      </div>
    </div>
  </div>
</template>

<script>
import {mapActions,mapState} from 'vuex'
import { createUser,checkOverlapped } from '@/api/signup.js'

export default {
  name: 'Signup',
  data () {
    return {
      nickname : '',
      isPossibleName : false,
      isOverlapped : true,
    }
  },
  computed : {
    ...mapState(['userStatus','kakaoId','userId']),
  },
  watch : {
    nickname : function() {
      this.nicknameValidate();
    },
  },
  methods : {
    ...mapActions(['setUserStatus','setAuthToken','setUserId']),
    onClickSignup() {
      const kakaoId = this.kakaoId;
      createUser(
        {
        "kakaoId" : kakaoId,
        "username" : this.nickname,
        },
        (res) => {
          const responseUserId = res.data.id;
          const authToken = res.data['accToken'];
          // const refreshToken = res.data['refToken']
          console.log(res.data)
          this.setUserId(responseUserId);
          this.setAuthToken(authToken);
          this.setUserStatus(true);
          this.$router.push({name : "Home"});
        },
        (err) => {
          console.log(err);
        }

      );
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
      console.log(this.nickname)
      checkOverlapped(
        {
          'username' : this.nickname,
        },
        (res) => {
          console.log(res)
          this.isOverlapped = res.data;
          if(this.isOverlapped) {
            alert('사용 불가능한 ❌❌ 아이디입니다');
            }
          else {
            alert('사용 가능한 ⭕⭕ 아이디입니다');
          }
        },
        (err) => {
          alert('err',err)
        }
      )
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

