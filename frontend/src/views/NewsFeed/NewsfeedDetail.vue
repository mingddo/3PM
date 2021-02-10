<template>
  <div>
    <Sidebar />
    <div class="newsfeed">
      <section v-if="fd" v-cloak class="feed-detail">
        <div class="feed-detail-userprofile-box">
          <div class="feed-userprofile-space">
              <img
              v-if="fd.user.img"
              :src="`https://dtbqjjy7vxgz8.cloudfront.net/${fd.user.img}`"
              onerror="this.src='http://image.yes24.com/momo/TopCate2600/MidCate6/259955881.jpg'"
              alt="유저프로필이미지"
              class="feed-userprofile-image"
              @click="goToProfile"
            >
            <img
              v-else
              src="http://image.yes24.com/momo/TopCate2600/MidCate6/259955881.jpg"
              alt="유저프로필이미지"
              class="feed-userprofile-image"
              @click="goToProfile"
            >
          </div>
          <div class="feed-detail-userprofile-content">
            <div>
              <h3 v-if="fd.user.nickname" class="feed-detail-userprofile-name" @click="goToProfile">{{fd.user.nickname}}</h3>
              <h3 v-else class="feed-detail-userprofile-name" @click="goToProfile">anonymous</h3>
            </div>
            
            <div>
              <p>{{ date }} {{ time }} </p>
            </div>
            
          </div>

        </div>

        <article class="feed-detail-content-box">
          <div v-if="fd.file">
            <img :src="`https://dtbqjjy7vxgz8.cloudfront.net/${fd.file}`" alt="업로드 파일" class="feed-detail-img">
          </div>
          <div v-if="Category == 2 || Category == 3">
            위치정보!
          </div>
          <div class="feed-detail-tag">
            <button v-for="(tag, idx) in fd.tags" :key="idx" @click="goToSearchTag(tag)"> {{ tag }}  </button>
          </div>
          <div class="feed-detail-content">
            <p v-html="fd.content">
              <!-- {{fd.content}} -->
            </p>
          </div>
        </article>

        <div class="feed-detail-modi-delete" v-if="userpk == fd.user.id">
          <button @click="changeModiForm">
            수정하기
          </button>
          <button @click="deleteFeed">
            삭제하기
          </button>
        </div>

        
        <div class="feed-detail-like-comment">
          <span>
              <i class="far fa-thumbs-up"></i>
              <span v-if="this.fd.likeCnt">{{ this.fd.likeCnt }}</span> <span v-else> 0</span>
          </span>
          <span>
            <i class="far fa-comment"></i>
            <span v-if="fd.commentCnt">{{ fd.commentCnt }}</span> <span v-else> 0</span>
          </span>
        </div>

        <div class="feed-detail-like-comment-share-btn-box">
          <div @click="likeFeed" class="feed-detail-like-comment-share-btn">
            <i class="far fa-thumbs-up"></i>
            좋아요
          </div>
          <div class="feed-detail-like-comment-share-btn" @click="focusComment">
            <i class="far fa-comment"></i>
            댓글달기
          </div>
          <div class="feed-detail-like-comment-share-btn" @click="shareFeed">
            <i class="fas fa-share"></i>
            공유하기
          </div>
        </div>

        <div class="feed-detail-comment-input-box">
          <img
            src="https://blog.cpanel.com/wp-content/uploads/2019/08/user-01.png"
            alt="유저프로필이미지"
            class="feed-detail-comment-img"
          >
          <Mentionable
            :keys="['@']"
            :items="items"
            offset="6"
          >
            <textarea class="feed-comment-input" placeholder="댓글을 입력해주세요" name="comment" id="comment" cols="30" rows="10" :value="commentInput" @keyup="commentKeyup"></textarea>
          <!-- <input class="feed-detail-comment-input" id="comment" type="text" placeholder="댓글을 입력해주세요" v-model.trim="commentInput" @keyup="mentionUser"> -->
          <!-- <input class="feed-detail-comment-input" id="comment" type="text" placeholder="댓글을 입력해주세요" :value="commentInput" @keyup="commentKeyup"> -->
          </Mentionable>
          <button class="feed-detail-comment-btn" @click="createComment"><i class="fas fa-plus"></i></button>
        </div>

        <section>
          <div v-for="(comment, idx) in fd.comment" :key="idx">
            <NewsFeedCommentItem
              :comment="comment"
            />
          </div>
        </section>
      </section>
    </div>
  </div>
</template>

<script>
import { Mentionable } from 'vue-mention'
import '@/assets/css/mention.css'
// import NewsFeedCommentItem from '../../components/NewsFeed/NewsFeedCommentItem.vue';
import { mapState } from 'vuex'
import { readFeed } from '@/api/feed.js'
import { deleteFeed } from '@/api/feed.js'
import { clapFeed } from '@/api/feed.js'
import Sidebar from '../../components/Common/Sidebar.vue';

export default {
  name: 'NewsfeedDetail',
  components: {
    Sidebar,
    Mentionable,
    // NewsFeedCommentItem,
  },
  data() {
    return {
      Category: null,
      fd: null,
      commentInput: "",
      date: null,
      time: null,
      mention: [],
      auto:false,
      items: [],
    };
  },
  // updated () {
  //   this.autoCompleted ();
  // },
  watch: {
    
  },
  methods: {
    commentKeyup (e) {
      this.commentInput = e.target.value;
      let lastChar = this.commentInput.charAt(this.commentInput.length-1)
      console.log(lastChar)
      if (!this.auto && lastChar == '@') {
        this.auto = true;
      } else if (this.auto && lastChar == ' ') {
        this.auto = false;
      }
      if (this.auto) {
        let results = this.commentInput.match(/@/g); 
        let cnt = results.length
        let mentionedUser = this.commentInput.split('@')[cnt]
        let saveMention = mentionedUser.split(' ')
        if (saveMention.length > 1 || e.code == 'Space' || e.code == 'Enter') {
          this.auto = false;
        } else {
          console.log('api요청 단어', this.commentInput)
          this.items = [
            {
              value: '1',
              label: '장수민',
            },
            {
              value: '2',
              label: '잔수민',
            },
            {
              value: '3',
              label: '자스민',
            },
            {
              value: '4',
              label: '장슈민',
            },
            {
              value: '5',
              label: '장수밍',
            },
          ]
        }
      }
    },
    likeFeed () {
      clapFeed(
        this.fd.id,
        (res) => {
          alert(`${this.fd.id} 번째 글을 좋아합니다.`)
          console.log(res)
        },
        (err) => {
          console.log(err)
        }
      )
      
      // like axios
    },
    shareFeed () {
      const answer = window.confirm('내 피드에 공유하시겠습니까?')
      if (answer) {
        this.$router.push({ name: 'NewsfeedForm', query: { Category: this.Category }, params: {type: 'SHARE', feed: this.fd, link: document.location.href, }})
      }
    },
    focusComment () {
      let input = document.getElementById('comment')
      input.focus();
    },
    deleteFeed () {
      if (this.userpk == this.fd.user.id) {
        const answer = window.confirm('정말로 삭제하시겠습니까?')
        if (answer) {
          if (this.Category == 1) {
            deleteFeed(
              this.fd.id,
              () => {
                this.$router.push({name: 'NewsfeedPersonal', query: { Category: 1}})
              },
              (err) => {
                console.log(err)
              }
            )
          } else if (this.Category == 2) {
            // 핵인싸 DELETE 요청
          } else if (this.Category == 3) {
            // 청산별고 DELETE 요청
          } else if (this.Category == 4) {
            // 워커홀릭 DELETE 요청
          }
        }
      }
    },
    changeModiForm () {
      this.$router.push({ name: 'NewsfeedForm', query: { Category: this.Category }, params: { type: 'MODI', feed: this.fd }})
    },
    createComment () {
      if (!this.commentInput) {
        alert('내용을 입력해주세요')
      } else {
        let results = this.commentInput.match(/@/g); 
        if (results == null) {
          console.log('새 댓글 작성', this.commentInput)
        } else {
          let cnt = results.length
          // console.log(cnt)
          for (let i = 1; i <= cnt; i++) {
            console.log(i)
            let slice = this.commentInput.split('@')[i]
            let mentioned = slice.split(' ')[0]
            if (mentioned) {
              this.mention.push(mentioned)
            }
          }
          console.log(this.mention)
        }

        // let mentioned = slice.split(' ')[0]
        // console.log(mentioned)
        // alert(`${this.commentInput} 내용의 댓글을 작성할게요`)
        // axios 댓글 create  요청 보내기 
        this.commentInput = ''
      }
    },
    goToProfile () {
      this.$router.push({ name: 'MyPage', query: { name: this.fd.user.id}})
    },  
    goToSearchTag (tag) {
      this.$router.push({ name: 'Search', query: { query: tag, filter: 'tag' }})
    },
    setFeedDetail () {
      // feed.pk 를 활용하여 detail 페이지 요청 보내기
      // 현재는 가상 데이터 하나만 고정해서 보여주기
      this.Category = this.$route.query.Category
      if (this.Category == 1) {
        readFeed (
          this.$route.query.id,
          (res) => {
            this.fd = res.data
            console.log(res.data)
            this.date = this.fd.date.split('T')[0];
            this.time = this.fd.date.split('T')[1];
          },
          (err) => {
            console.log(err)
          }
        )
      } else if (this.Category == 2) {
        // 핵인싸 get 요청
      } else if (this.Category == 3) {
        // 청산별곡 get 요청
      } else if (this.Category == 4) {
        // 워커홀릭 get 요청
      }
    },
  },
  created () {
    this.setFeedDetail();
  },
  computed: {
    ...mapState({
      userpk : (state) => state.userId,
    })
  },
};
</script>

<style scoped>
.newsfeed {
  position: absolute;
  top : 80px;
}
.feed-comment-input {
  width: 100%;
  height: 50px;
}
</style>