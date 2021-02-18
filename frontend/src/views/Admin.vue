<template>
  <div>
    <div class="container">  
      <p>카카오 아이디를 입력해주세요.</p>
      <div class="signup-input">
        <input @input="kakaoId = $event.target.value" type="text" placeholder="카카오아이디 중복되지 않게 해주세용">
      </div>

      <div class="signup-input">
        <button @click="onClickLogin">로그인</button>
      </div>
    </div>
  </div>
</template>

<script>
import {mapActions,mapState} from 'vuex'
import { getUser } from "@/api/login.js";

export default {
  name: 'Signup',
  data () {
    return {
      nickname : '',
      isPossibleName : false,
      isOverlapped : true,
      checkedName : '',
      kakaoId : '',
    }
  },
  computed: {
    ...mapState([
      "userStatus",
      "kakaoId",
      "userId",
      "authToken",
      "userInfo",
      "refToken",
    ]),
  },
  methods : {
    ...mapActions(['setUserStatus','setKakaoId','setAuthToken','setRefToken','setUserId']),
    onClickLogin(){
      getUser(
        {
          kakaoId: this.kakaoId,
        },
        (res) => {
          this.setKakaoId(this.kakaoId);
          // true -> user 정보가 있으면  Home
          if (res.data) {
            // 세션에 토큰 설정
            // console.log("getUser 정보", res.data);
            const responseUserId = res.data.id;
            const authToken = res.data["accToken"];
            const refToken = res.data["refToken"];
            this.setUserId(responseUserId);
            this.setAuthToken(authToken);
            this.setRefToken(refToken);
            this.setUserStatus(true);
            this.$swal.fire({
              icon: 'success',
              text: '로그인 완료',
              showConfirmButton: false,
              timer: 1500
            })
          this.$router.push({ name: "Home" });

          }
          // false -> user 정보가 없으면 Signup
          else {
            // 세션에 카카오 ID 설정
            this.$router.push({ name: "Signup" });
          }
        },
        (err) => {
          console.log(err);
        })
      }
  }
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
  .disabledBtn {
    opacity: .2;
  }
  .container p {
    margin-bottom: 10px;
  }
</style>

