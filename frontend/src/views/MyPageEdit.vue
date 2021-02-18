<template>
  <div class="newsfeed-body">
    <Sidebar />
    <div class="mypageEdit">
      <div class="mypageEdit-Toggle-Img" v-if="editing">
        <Practice
          :userImg="userInfo.user_img"
          @edit_url="edit_url"
          @endUrlEdit="endUrlEdit"
          @previewURL="previewURL"
        />
      </div>
      <div class="mypageEdit-Toggle-Img" v-else>
        <div class="myPageEdit-Header-Title">
          내 정보 수정
        </div>
        <div class="profile-edit-img-frame">
          <div v-if="!preview">
            <img
              v-if="userInfo.user_img"
              :src="`https://dtbqjjy7vxgz8.cloudfront.net/${userInfo.user_img}`"
              alt=""
            />
            <img v-else src="@/assets/img/profileM.svg" alt="" width="80px" />
          </div>
          <div v-else>
            <img :src="preview_URL" alt="" />
          </div>
        </div>

        <button @click="editing = true" class="edit-profile-Img-btn">
          프로필 이미지 수정
        </button>

        <div class="nicknameEdit-frame">
          <div class="nicknameexist" v-if="!nicknameedit">
            <div>
              <span class="highlight">닉네임</span> : {{ userInfo.nickname }}
            </div>
            <button
              class="nicknameEdit-btn"
              @click="nicknameedit = !nicknameedit"
            >
              수정
            </button>
          </div>

          <div class="nicknameEditForm" v-else>
            <div class="nickname-warn">
              <span>※ 닉네임은</span>
              <span class="highlight-warn"
                >영어/한글/숫자 4자 이상 10자 이내로</span
              >
              <span>입력 가능합니다.</span>
            </div>
            <div class="nickname-edit-input-frame">
              <input
                v-model="nickname"
                type="text"
                placeholder="영어/한글/숫자 4자 이상 10자 이내로 입력"
              />
              <button
                @click="checkOverlap"
                :disabled="!isPossibleName"
                class="edit-dupli-btn"
                :class="{ disabledBtn: !isPossibleName }"
              >
                중복확인
              </button>
            </div>
            <div class="nickname-edit-input-frame-btn">
              <button class="edit-nickname-back-btn" @click="backtoEdit">
                돌아가기
              </button>
              <button
                class="edit-nickname-confirm-btn"
                @click="changenickname"
                :disabled="isOverlapped"
                :class="{ disabledBtn: isOverlapped }"
              >
                닉네임 수정하기
              </button>
            </div>
          </div>
        </div>

        <div class="profile-intro-frame">
          <div class="profile-intro-frame-title-container">
            <div class="highlight">프로필 소개</div>
            <button
              v-if="!introduceedit"
              class="intro-Edit-btn"
              @click="introduceedit = !introduceedit"
            >
              수정
            </button>
          </div>
          <div class="prrfile-content-frame" v-if="!introduceedit">
            <div class="intro-content" v-if="userInfo.introduce">
              {{ userInfo.introduce }}
            </div>
            <div v-else>
              프로필 소개가 아직 없습니다.
            </div>
          </div>

          <div v-else class="introEdit-frame">
            <input type="text" v-model="introduce" />
            <div class="nickname-edit-input-frame-btn">
              <button class="edit-nickname-back-btn" @click="introgoback">
                돌아가기
              </button>
              <button class="edit-nickname-confirm-btn" @click="saveintroduce">
                저장하기
              </button>
            </div>
          </div>
        </div>

        <button
          :disabled="disabled_valid"
          class="save-Edit-profile"
          :class="[disabled_valid ? 'myPageEdit_valid' : '']"
          @click="editProfile"
        >
          저장하기
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import Practice from "../components/Practice.vue";
import { userInfoDetail, editUserInfo } from "@/api/mypage.js";
import { checkOverlapped } from "@/api/signup.js";
import Sidebar from "../components/Common/Sidebar.vue";

export default {
  components: { Practice, Sidebar },
  data() {
    return {
      editing: false,
      nicknameedit: false,
      introduceedit: false,
      userInfo: {},
      writeIntroduce: false,
      changeURL: null,
      preview_URL: null,
      preview: false,
      nickname: "",
      isPossibleName: false,
      isOverlapped: true,
      introduce: "",
      completed: false,
    };
  },
  computed: {
    disabled_valid() {
      if (this.editing || this.nicknameedit || this.introduceedit) {
        return true;
      } else {
        return false;
      }
    },
  },
  methods: {
    introgoback() {
      this.introduceedit = false;
      this.introduce = this.userInfo.introduce == null ? '' : this.userInfo.introduce;
    },
    saveintroduce() {
      this.userInfo.introduce = this.introduce;
      this.introduceedit = false;
    },
    changenickname() {
      this.userInfo.nickname = this.nickname;
      this.nicknameedit = false;
    },
    previewURL(a) {
      this.preview_URL = a;
      this.preview = true;
    },
    getUserProfileInfo() {
      userInfoDetail(
        this.$store.state.userId,
        (res) => {
          this.userInfo = res.data;
          this.nickname = res.data.nickname;
          this.introduce = res.data.introduce;
        },
        (err) => {
          console.error(err);
        }
      );
    },
    backtoEdit() {
      this.nicknameedit = false;
      this.nickname = this.userInfo.nickname;
    },
    editProfile() {
      const formData = new FormData();
      if (this.changeURL) {
        formData.append("file", this.changeURL);
      }
      formData.append("nickname", this.userInfo.nickname);
      formData.append("introduce", this.userInfo.introduce);
      editUserInfo(
        this.$store.state.userId,
        formData,
        () => {
          this.$swal.fire({
            icon: 'success',
            text: '내 정보가 수정되었어요',
            showConfirmButton: false,
            timer: 1500
          })
          this.completed = true;
          this.$router.push({
            name: "MyPage",
            query: { name: this.$store.state.userId },
          });
        },
        (err) => {
          console.error(err);
        }
      );
    },
    edit_url(change_url) {
      this.changeURL = change_url;
    },
    endUrlEdit(e) {
      this.editing = e;
    },
    nicknameValidate() {
      const nickname = this.nickname;
      const nicknamePattern = /^[a-zA-Z0-9가-힣]{4,10}$/;
      const patternCheck = nicknamePattern.test(nickname);
      if (nickname.length >= 10 || !patternCheck) {
        this.isPossibleName = false;
      } else {
        this.isPossibleName = true;
      }
    },
    checkOverlap() {
      // DB에 중복된 닉네임이 있는지 확인하여 회원가입 버튼 활성화
      if (this.userInfo.nickname === this.nickname) {
        this.$swal.fire({
          icon: 'error',
          text: '현재 닉네임과 같습니다',
          showConfirmButton: false,
          timer: 1500
        })
      } else {
        checkOverlapped(
          {
            username: this.nickname,
          },
          (res) => {
            this.isOverlapped = res.data;
            if (this.isOverlapped) {
              this.$swal.fire({
                icon: 'error',
                text: '사용 불가능한 닉네임입니다',
                showConfirmButton: false,
                timer: 1500
              })
            } else {
              this.$swal.fire({
                icon: 'success',
                text: '사용 가능한 닉네임입니다',
                showConfirmButton: false,
                timer: 1500
              })
            }
          },
          (err) => {
            console.log(err)
            this.$swal.fire({
              icon: 'error',
              showConfirmButton: false,
              timer: 1500
            })

          }
        );
      }
    },
  },
  created() {
    if (!this.$store.state.userStatus) {
      this.$router.push({name : "Home"});
    }
    this.getUserProfileInfo();
  },
  beforeRouteLeave(to, from, next) {
    if (this.completed) {
      next();
    } else {
      this.$swal.fire({
        icon: 'warning',
        text: '수정 중인 내용이 저장되지 않았습니다. 화면을 나가시겠습니까?',
        showCancelButton: true,
        confirmButtonText: '나가기', cancelButtonText: '돌아가기'
      }).then((result) => {         
        if (result.isConfirmed) { 
          next();       
        } else {
          next(false);
        }
      })

    }
  },
  watch: {
    nickname: function() {
      this.nicknameValidate();
    },
  },
};
</script>

<style scoped>
::placeholder {
  color: rgba(0, 0, 0, 0.5);
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
  padding: 0.25rem 1rem;
  background: none;
  border: none;
  box-shadow: 0px 5px 10px rgba(0, 0, 0, 0.2);
  border-radius: 30px;
  cursor: pointer;
  font-weight: bold;
  color: #585858;
}
.disabledBtn {
  opacity: 0.2;
}
</style>
