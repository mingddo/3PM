<template>
  <div class="newsfeed">
    <NewsFeedHeader
      :Category="Category"
      class="newsfeed-header"
    />
    <section class="feed-detail">
      <div class="feed-detail-userprofile-box">
        <img
          src="https://blog.cpanel.com/wp-content/uploads/2019/08/user-01.png"
          alt="유저프로필이미지"
          class="feed-detail-userprofile-img"
          @click="goToProfile"
        >
        <div class="feed-detail-userprofile-content">
          <div>
            <h3 class="feed-detail-userprofile-name" @click="goToProfile">{{fd.user.nickname}}</h3>
          </div>
          
          <div>
            <p>{{ fd.date }}</p>
          </div>
          
        </div>

      </div>

      <article class="feed-detail-content-box">
        <div v-if="fd.file">
          <img :src="fd.file" alt="업로드 파일" class="feed-detail-img">
        </div>
        <div class="feed-detail-tag">
          <button v-for="(tag, idx) in fd.tag" :key="idx" @click="goToSearchTag(tag)"> {{ tag }}  </button>
        </div>
        <div class="feed-detail-content">
          <p>
            {{fd.content}}
          </p>
        </div>
      </article>

      <div class="feed-detail-modi-delete">
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
            {{ fd.like.length }}
        </span>
        <span>
          <i class="far fa-comment"></i> 댓글 {{ fd.comment.length }}개
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
        <input class="feed-detail-comment-input" id="comment" type="text" placeholder="댓글을 입력해주세요" v-model.trim="commentInput" @keyup.enter="createComment">
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
</template>

<script>
import NewsFeedCommentItem from '../../components/NewsFeed/NewsFeedCommentItem.vue';
import NewsFeedHeader from '../../components/NewsFeed/NewsFeedHeader.vue';

export default {
  name: 'NewsfeedDetail',
  components: {
    NewsFeedHeader,
    NewsFeedCommentItem,
  },
  data() {
    return {
      Category: this.$route.query.Category,
      fd: "",
      commentInput: "",
    };
  },
  methods: {
    likeFeed () {
      alert(`${this.fd.id} 번째 글을 좋아합니다.`)
      // like axios
    },
    shareFeed () {
      const answer = window.confirm('내 피드에 공유하시겠습니까?')
      if (answer) {
        this.$router.push({ name: 'NewsfeedForm', query: { Category: this.Category }, params: {type: 'SHARE', feed: this.fd }})
      }
    },
    focusComment () {
      let input = document.getElementById('comment')
      input.focus();
    },
    deleteFeed () {
      const answer = window.confirm('정말로 삭제하시겠습니까?')
      if (answer) {
        //axios 요청으로 DELETE 보내기
      }
    },
    changeModiForm () {
      this.$router.push({ name: 'NewsfeedForm', query: { Category: this.Category }, params: { type: 'MODI', feed: this.fd }})
    },
    createComment () {
      if (!this.commentInput) {
        alert('내용을 입력해주세요')
      } else {
        alert(`${this.commentInput} 내용의 댓글을 작성할게요`)
        // axios 댓글 create  요청 보내기 
        this.commentInput = ''
      }
    },
    goToProfile () {
      this.$router.push({ name: 'MyPage', query: { name: this.fd.user.nickname}})
    },  
    goToSearchTag (tag) {
      this.$router.push({ name: 'Search', query: { query: tag, filter: 'tag' }})
    },
    setFeedDetail () {
      // feed.pk 를 활용하여 detail 페이지 요청 보내기
      // 현재는 가상 데이터 하나만 고정해서 보여주기
      this.fd = {
        'id' : 3,
        'content' : '세번째 피드입니다  뭐하냐',
        'date' : '2020-01-29',
        'tag' : ['테스트', '세번째'],
        'file' : `https://images.mypetlife.co.kr/content/uploads/2019/07/24140006/zoltan-tasi-qLXa-miMhbw-unsplash-e1563944529868.jpg`,
        'user': {
          'id' : 5,
          'nickname' : '차민석',
        },
        'like' : [
          {
            'id' : 1,
            'user': {
              'id' : 10,
              'nickname' : '장수민'
            },
            'date' : '2021-01-31',
          },
          {
            'id' : 2,
            'user': {
              'id' : 11,
              'nickname' : '명도균'
            },
            'date' : '2021-01-30',
          },
        ],
        'comment' : [
          {
            'id' : 1,
            'user': {
              'id' : 11,
              'nickname' : '명도균'
            },
            'date' : '2021-01-31',
            'content' : '훌륭합니다!',
            'like' : [
              {
                'id' : 1,
                'user': {
                  'id' : 10,
                  'nickname' : '장수민'
                },
                'date' : '2021-01-31',
              },
            ],
            'nested_comment': [
              {
                'id' : 1,
                'user': {
                  'id' : 10,
                  'nickname' : '장수민'
                },
                'date' : '2021-01-31',
                'content' : '대댓글!!',
                'like' : [
                  {
                    'id' : 1,
                    'user': {
                      'id' : 10,
                      'nickname' : '장수민'
                    },
                    'date' : '2021-01-31',
                  },
                ],
              },
            ],
          },
        ],
      };
    },
  },
  created () {
    this.setFeedDetail();
  },
};
</script>

<style scoped>
.feed-detail {
  border: 1px solid;
  padding: 10px;
}
.feed-detail-userprofile-box {
  display: flex;
}
.feed-detail-userprofile-img {
  cursor: pointer;
  width: 10%;
  margin: 10px;
}
.feed-detail-userprofile-content {
  margin: 10px;
  text-align: left;
  align-items: bottom;
}
.feed-detail-userprofile-name {
  cursor: pointer;
}
.feed-detail-tag {
  text-align : left;
  margin: 10px;
}
.feed-detail-content-box {
  width: 100%;
}
.feed-detail-img {
  width: 100%;
}
.feed-detail-content {
  margin: auto;
  padding: 10px;
  border: 1px solid;
  text-align: left;
  width: 95%;
}
.feed-detail-modi-delete {
  margin: 10px;
  text-align: right;
}
.feed-detail-like-comment {
  margin: 10px;
  display: flex;
  justify-content: space-between;
}
.feed-detail-like-comment-share-btn-box {
  display: flex;
  justify-content: space-evenly;
  border-top: 1px solid lightgray;
  border-bottom: 1px solid lightgray;
}
.feed-detail-like-comment-share-btn {
  display: flex;
  width: 100%;
  height:50px;
  margin: 2px;
  border-radius: 5px;
  cursor: pointer;
  align-items: center;
  justify-content: center;
}
.feed-detail-like-comment-share-btn:hover{
  background-color: rgb(233, 232, 232);
}
.feed-detail-comment-input-box {
  display: flex;
  width: 100%;
}
.feed-detail-comment-img {
  margin: 10px;
  width: 5%;
}
.feed-detail-comment-input {
  margin: 10px;
  width: 90%;
  padding: 5px;
}
.feed-detail-comment-btn {
  font-size: 20px;
  margin: 10px;
  width: 5%;
  padding: 5px;
}
</style>