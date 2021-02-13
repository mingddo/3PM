<template>
  <article class="feed-comment">
    <div>
      <div class="feed-comment-userprofile-box">
        <div class="feed-comment-userprofile">
          <NewsFeedProfile
            v-if="comment.user.img"
            :proImg="comment.user.img"
            :userId="comment.user.id"
          />
          <NewsFeedProfile
            v-else
            :proImg="'20210205132713974_img1.jpg'"
            :userId="comment.user.id"
          />
          <!-- <img
            src="https://blog.cpanel.com/wp-content/uploads/2019/08/user-01.png"
            alt="유저프로필이미지"
            class="feed-comment-userprofile-img"
            @click="goToProfile"
          > -->
          <div class="feed-comment-userprofile-content">
            <h3 class="feed-comment-userprofile-name" @click="goToProfile">{{ comment.user.nickname }}</h3>
            <p class="feed-comment-userprofile-date">{{ comment.date }} </p>
          </div>
        </div>
        <!--댓글 작성자 본인의 경우 // vuex 저장 내용으로 user 정보 비교하여 확인-->
        <div v-if="comment.user.id == userpk">
          <i class="fas fa-bars feed-comment-btn-drop" @click="openModiDeleteBtn"></i>
          <div v-show="!foldModiDrop" class="feed-comment-btn-drop-content">
            <div @click="changeCommentModiForm">
              수정
            </div>
            <div @click="deleteComment">
              삭제
            </div>
            
          </div>
        </div>
      </div>
      <div class="feed-comment-content-box" v-if="!modiForm">
        {{ commentForFeed }}
      </div>
      <div v-else class="feed-comment-modiInput-box">
        <input class="feed-comment-modiInput" type="text" v-model="commentForFeed" @keyup.enter="modiComment">
        <button class="feed-comment-modiInput-btn" @click="modiComment">
          <i class="fas fa-check"></i>
        </button>
        
      </div>

      <div class="feed-comment-like-nested">
        <!-- <div class="feed-comment-like-btn">
          <i class="far fa-comment">
            {{ comment.nested_commentCnt }}
          </i>
        </div> -->
        <div class="feed-comment-like-btn">
          <i class="far fa-thumbs-up" @click="likeComment">
            {{ comment.likeCnt }}
          </i>
        </div>
        <div>
          <i class="fas fa-quote-left" @click="mentionUSer">
            소환
          </i>
        </div>
      </div>
    </div>

    <!-- <div v-if="!foldNested">
      <div class="feed-comment-nested-box">
        <img
          src="https://blog.cpanel.com/wp-content/uploads/2019/08/user-01.png"
          alt="유저프로필이미지"
          class="feed-comment-nested-img"
        >
        <input class="feed-comment-nested-input" type="text" v-model.trim="nestedCommentInput" @keyup.enter="createNestedComment" placeholder="답글을 입력해주세요">
        <button class="feed-comment-nested-btn" @click="createNestedComment"><i class="fas fa-plus"></i></button>
      </div>

      <div v-for="(nestedComment, idx) in comment.nested_comment" :key="idx" class="feed-comment-nested-box">
        <NewsFeedCommentNested
          :comment="nestedComment"
        />
      </div>
      <div @click="changeNested" class="feed-comment-nested-close-btn">
        답글 접기
      </div>
    </div> -->
  </article>
</template>

<script>
// import NewsFeedCommentNested from './NewsFeedCommentNested.vue';
import NewsFeedProfile from './Common/NewsFeedProfile.vue';
import { mapState } from 'vuex'
export default {
  name: 'NewsFeedCommentItem',
  components: { NewsFeedProfile },
  props: {
    comment: Object,
  },
  data() {
    return {
      // foldNested: true,
      nestedCommentInput: "",
      modiForm: false,
      commentForFeed: this.comment.content,
      foldModiDrop: true,
    };
  },
  methods: {
    mentionUSer () {
      this.$emit('pushUserToComment', this.comment.user.nickname)
    },
    createNestedComment () {
      if (!this.nestedCommentInput) {
        alert('내용을 입력해주세요')
      } else {
        alert(`${this.nestedCommentInput} 내용의 댓글을 작성할게요`)
        // axios 대댓글 create  요청 보내기 
        this.nestedCommentInput = ''
      }
    },
    changeNested () {
      this.foldNested = !this.foldNested;
    },
    likeComment () {
      // 댓글 좋아요 axios 요청
      alert(`${this.comment.content}를 좋아합니다!`)
    },
    deleteComment () {
      // 유저 본인 맞는 지 확인 한 번 하고
      this.foldModiDrop = true;
      const answer = window.confirm('댓글을 삭제하시겠습니까?')
      if (answer) {
        // axios DELETE 요청으로 삭제하기
      }
    },
    changeCommentModiForm () {
      this.modiForm = true;
      this.foldModiDrop = true;
    },
    modiComment () {
      this.modiForm = false;
      // axios put 요청으로 수정하기
    },
    openModiDeleteBtn () {
      this.foldModiDrop = !this.foldModiDrop
    },
    goToProfile () {
      this.$router.push({ name: 'MyPage', query: { name: this.comment.user.nickname}})
    },
  },
  computed: {
    ...mapState({
      userpk : (state) => state.userId,
      defaultImg: (state) => state.defaultImg,
    })
  },
};
</script>

<style>
.feed-comment {
  margin: 20px;
}
.feed-comment-userprofile-box {
  display: flex;
  justify-content: space-between;
}
.feed-comment-userprofile {
  display: flex;
  justify-content: left;
}
.feed-comment-userprofile-img {
  width: 5%;
  cursor: pointer;
}
.feed-comment-userprofile-content {
  text-align: left;
  margin: 0px;
  margin-left: 10px;
}
.feed-comment-userprofile-name {
  margin: 0px;
  cursor: pointer;
}
.feed-comment-userprofile-date {
  margin: 0px;
}
.feed-comment-btn-drop {
  cursor: pointer;
  position: relative;
}

.feed-comment-btn-drop-content {
  /* text-align: center; */
  /* padding: 10px; */
  width: 70px;
  z-index: 2;
  position: absolute;
  right: 0;
  background-color: rgb(236, 236, 236);
  /* padding: 10px; */
  border-radius: 5px;
  text-align: center;
  margin-right: 20px;
}
.feed-comment-btn-drop-content > div {
  border-bottom: 1px solid rgb(155, 155, 155);;
  padding : 10px
}
.feed-comment-btn-drop-content > div:hover {
  box-shadow: 0px 5px 10px rgba(0,0,0,0.2);
}
.feed-comment-btn-drop-content > div:last-child {
  border-bottom: 0px;
}
.feed-comment-content-box {
  margin: 10px;
  padding: 10px;
  text-align: left;
}
.feed-comment-modiInput-box {
  display: flex;
  margin: 10px;
  width: 100%;
  padding: 5px;
}
.feed-comment-modiInput {
  margin: 5px;
  width: 95%;
  padding: 5px;
}
.feed-comment-modiInput-btn {
  margin: auto;
  padding: 10px;
  width: 10%;
}
.feed-comment-like-nested {
  display: flex;
}
.feed-comment-like-nested > div {
  cursor: pointer;
  margin: 10px;
}
.feed-comment-like-btn {
  cursor: pointer;
  margin: 10px;
}
.feed-comment-nested-box {
  display: flex;
  width: 95%;
  margin-left: auto;
}
.feed-comment-nested-img {
  margin: 10px;
  width: 5%;
}
.feed-comment-nested-input {
  margin: 10px;
  width: 90%;
  padding: 5px;
}
.feed-comment-nested-btn {
  font-size: 20px;
  margin: 10px;
  width: 5%;
  padding: 5px;
}
.feed-comment-nested-close-btn {
  padding: 10px;
  cursor: pointer;
}
.feed-comment-nested-close-btn:hover {
  border: 1px solid rgb(202, 202, 202);
  border-radius: 5px;
}
</style>