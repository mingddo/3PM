<template>
  <article class="feed-comment">
    <div>
      <div class="feed-comment-userprofile-box">
        <div class="feed-comment-userprofile">
          <NewsFeedProfile
            :proImg="comment.user.img ? comment.user.img : defaultImg"
            :userId="comment.user.id"
          />

          <div class="feed-comment-userprofile-content">
            <h3 class="feed-comment-userprofile-name" @click="goToProfile">{{ comment_info.user.nickname }}</h3>
            <p class="feed-comment-userprofile-date">{{ agoDate != '0일전' ? agoDate : '오늘' }} </p>
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
        <div class="feed-comment-like-btn">
          <img :src="comment.clap ? 'https://img.icons8.com/fluent-systems-filled/14/000000/applause.png' : 'https://img.icons8.com/fluent-systems-regular/14/000000/applause.png'" @click="likeComment"/>
            <span>{{ comment_info.clapCnt }}</span>
          <!-- </i> -->
        </div>
        <div @click="mentionUSer">
          <span>{{ comment_info.user.nickname }} 님을 언급</span>
          <img src="https://img.icons8.com/metro/14/000000/very-popular-topic.png"/>
        </div>
      </div>
    </div>
  </article>
</template>

<script>
import NewsFeedProfile from '../Common/NewsFeedProfile.vue';
import { updateComment } from '@/api/comment.js'
import { deleteComment } from '@/api/comment.js'
import { clapComment } from '@/api/comment.js'
import { mapState } from 'vuex'
export default {
  name: 'NewsFeedCommentItem',
  components: { NewsFeedProfile },
  props: {
    Category: Number,
    comment: Object,
    id: Number,
  },
  data() {
    return {
      modiForm: false,
      commentForFeed: this.comment.content,
      foldModiDrop: true,
      comment_info: this.comment,
      agoDate: null,
      time: null,
      year: null,
      month: null,
      day: null,
    };
  },
  mounted () {
    this.setDateTime();
  },
  methods: {
    setDateTime () {
      let d = new Date ();
      let todayDate = d.getDate();
      let todayMonth = d.getMonth() + 1;
      console.log(todayMonth)
      if (this.comment) {
        let date = this.comment.date.split('T')[0]
        this.time = this.comment.date.split('T')[1]
        this.year = date.split('-')[0]
        this.month = date.split('-')[1]
        this.day = date.split('-')[2]
        if (this.month == todayMonth) {
          let prevDay = todayDate - this.day
          if (prevDay < 7) {
            this.agoDate = `${prevDay}일전`
          } else if (prevDay < 14){
            this.agoDate = `1주전`
          } else if (prevDay < 21) {
            this.agoDate = '2주전'
          } else if (prevDay < 28) {
            this.agoDate = '3주전'
          } else {
            this.agoDate = '4주전'
          }
        } else {
          let prevMon  = todayMonth - this.month
          if (todayMonth < this.month) {
            prevMon = 12 - this.month + todayMonth
          }
          if (prevMon < 4) {
            this.agoDate = `${prevMon}개월전`
          } else {
            this.agoDate = `수개월전`
          }
          
        }
      }
    },
    mentionUSer () {
      this.$emit('pushUserToComment', this.comment.user.nickname)
    },
    likeComment () {
      // 댓글 좋아요 axios 요청
      clapComment(
        this.comment.id,
        () => {
          if (this.comment_info.clap) {
            alert(`박수 끝~`)
            this.comment_info.clap = false;
            this.comment_info.clapCnt -= 1
          } else {
            alert(`일동 박수~👏`)
            this.comment_info.clap = true;
            this.comment_info.clapCnt += 1
          }
        },
        (err) => {
          console.log(err)
        }
      )
      
    },
    deleteComment () {
      if (this.userpk !== this.comment.user.id) {
        return alert('접근이 불가합니다!')
      }
      this.foldModiDrop = true;
      const answer = window.confirm('댓글을 삭제하시겠습니까?')
      if (answer) {
        // axios DELETE 요청으로 삭제하기
        if (this.Category == 1) {
          deleteComment(
            this.id,
            this.comment.id,
            (res) => {
              console.log('삭제', res)
            },
            (err) => {
              console.log('삭제불가', err)
            }
          )
        }
      }
    },
    changeCommentModiForm () {
      this.modiForm = true;
      this.foldModiDrop = true;
    },
    modiComment () {
      if (this.Category == 1) {
        updateComment(
          this.id,
          this.comment.id,
          {"content":this.commentForFeed},
          (res)=> {
            console.log(res)
          },
          (err)=>{
            console.log(err)
          }
        )
      }
      this.modiForm = false;
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
  align-items: center;
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
  justify-content: space-between;
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