<template>
  <div>
    <div class="container">
      <p>사용하실 닉네임을 입력해주세요.</p>
      <div class="signup-input">
        <input @input="nickname = $event.target.value" type="text" placeholder="영어/한글/숫자 4자 이상 10자 이내로 입력" @keyup="nicknameValidate" @keydown="nicknameValidate">
        <div class="signup-guide" v-if="!isPossibleName" style="color:red;">영어/한글/숫자 4자 이상 10자 이내로 입력</div>
        <div class="signup-guide" v-if="!isOverlapped" style="color:var(--bold-brown"> 사용가능한 닉네임 입니다. </div>
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
      checkedName : '',
    }
  },
  computed : {
    ...mapState(['userStatus','kakaoId','userId']),
  },
  watch : {
    isPossibleName : function() {
      if (this.isPossibleName === false) {
        this.isOverlapped = true
      }
    },
    nickname : function() {
      if (this.nickname !== this.checkedName) {
        this.isOverlapped = true
      }
    }
  },
  mounted() {
    if (!this.$store.state.kakaoId) {
      this.$router.push({name : "Home"});
    }
    if(this.userStatus) {
      this.$swal.fire({
        icon: 'success',
        text: '회원가입 하셨어요',
        showConfirmButton: false,
        timer: 1500
      })
      this.$router.push({name : "Home"});
    }
  },
  methods : {
    ...mapActions(['setUserStatus','setAuthToken','setRefToken','setUserId']),
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
          const refToken = res.data["refToken"];

          this.setUserId(responseUserId);
          this.setAuthToken(authToken);
          this.setRefToken(refToken);
          this.setUserStatus(true);
          this.$router.push({name : "Home"});
          this.$swal.fire({
            icon: 'success',
            text: '회원가입 완료',
            showConfirmButton: false,
            timer: 1500
          })
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
      checkOverlapped(
        {
          'username' : this.nickname,
        },
        (res) => {
          this.isOverlapped = res.data;
          if(this.isOverlapped) {
            this.$swal.fire({
              icon: 'error',
              text: '사용 불가능한 아이디입니다',
              showConfirmButton: false,
              timer: 1500
            })
          }
          else {
            this.checkedName = this.nickname;
            this.$swal.fire({
              icon: 'success',
              text: '사용 가능한 아이디입니다',
              showConfirmButton: false,
              timer: 1500
            })
          }
        },
        (err) => {
          console.log(err);
          this.$swal.fire({
            icon: 'error',
            showConfirmButton: false,
            timer: 1500
          })
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
  .container * {
    max-width: 800px;
  }
  .container p {
    font-size: 1.15rem;
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
    /* margin-bottom: 1rem; */
  }
  .signup-input button {
    margin-top: 1rem;
    background-color: #b29887;
    color: black;
    border-radius: 5px;
    width: 100%;
    height: 50px;
    padding : 0.25rem 1rem;
    border: none;
    box-shadow: 0px 5px 10px rgba(0,0,0,0.2);
    cursor: pointer;
    font-weight: bold;
  }
  .signup-guide {
    margin-left: 10px;
  }
  .disabledBtn {
    opacity: .2;
  }
  .container p {
    margin-bottom: 10px;
  }
</style>

