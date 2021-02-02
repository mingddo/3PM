<template>
  <div>
    <div>
      <Sidebar/>
    </div>
      <div id="newsfeed" class="newsfeed" onscroll="scrollFunction">
    <NewsFeedHeader
      :Category="Category"
      class="newsfeed-header"
    />
    <!-- <NewsFeedRecommend 
      :reco="reco"
      :Category="Category"
    /> -->
    <NewsFeedList
      :feed="feed"
      :Category="Category"
    />
  </div>
  </div>

</template>

<script>
import NewsFeedHeader from '../../components/NewsFeed/NewsFeedHeader.vue';
import NewsFeedList from '../../components/NewsFeed/NewsFeedList.vue';
import Sidebar from '../../components/Common/Sidebar.vue';
// import NewsFeedRecommend from '../../components/NewsFeed/NewsFeedRecommend.vue';

import { feedList } from '@/api/feed.js'

export default {
  name: 'NewsfeedPersonal',
  components: {
    NewsFeedHeader,
    // NewsFeedRecommend,
    NewsFeedList,
    Sidebar,
  },
  data () {
    return {
      feed: null,
      reco: [
        {
          'id' : 1,
          'content' : '첫번째 피드입니다',
          'date' : '2020-01-29',
          'tag' : ['테스트', '첫번째'],
          'file' : `https://newsimg.hankookilbo.com/cms/articlerelease/2019/04/29/201904291390027161_3.jpg`,
          'user': {
            'id' : 5,
            'nickname' : '이병훈',
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
        },
        {
          'id' : 2,
          'content' : '두번째 피드입니다',
          'date' : '2020-01-29',
          'tag' : ['테스트', '두번째'],
          'file' : `https://newsimg.hankookilbo.com/cms/articlerelease/2019/04/29/201904291390027161_3.jpg`,
          'user': {
            'id' : 5,
            'nickname' : '김상원',
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
        },
        {
          'id' : 3,
          'content' : '세번째 피드입니다',
          'date' : '2020-01-29',
          'tag' : ['테스트', '세번째'],
          'file' : ``,
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
        },
      ],
      Category: this.$route.query.Category,
      height: 0,

    }
  },
  methods: {
    setFeedList () {
      feedList(
        0,
        (res) => {
          console.log(res)
          this.feed = res.data.indoorResponseDtoList
        },
        (err) => {
          console.log(err)
        }
      )
    },
    setScroll () {
      const obj = document.getElementById('newsfeed');
      // console.log(obj)
      this.height = obj.scrollHeight;
      console.log(this.height)
    },
    

  },
  created () {
    this.setFeedList();
  },
  mounted () {
    this.setScroll();
  },
  
};
</script>

<style>

</style>
