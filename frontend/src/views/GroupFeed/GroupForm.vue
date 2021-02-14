<template>
  <div>
    새 그룹 만들기
    <div>
      <div>
        <input
          type="text"
          name="group_title"
          placeholder="그룹 이름을 입력해주세요"
          v-model="form.name"
        />
        <textarea
          name="groupintroduce"
          placeholder="그룹 설명을 입력해주세요"
          cols="30"
          rows="10"
          v-model="form.description"
        ></textarea>
      </div>

    </div>

    <button @click="create">그룹만들기</button>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { createGroup } from '@/api/group.js'

export default {
  data(){
    return{
      form: {
        name: "",
        description: "",
      },
      completed: false,
    }
  },
  methods:{
    create () {
      createGroup(
        this.form,
        (res) => {
          console.log(res)
          this.$router.push({ name: 'groupdetail', query: { groupId: res.data}})
        },
        (err) => {
          console.log(err)
        }
      )
    },
  },
    beforeRouteLeave (to, from, next) {
    if (this.completed) {
      next();
    } else {
      const answer =
        window.confirm('작성 중인 내용이 저장되지 않았습니다. 화면을 나가시겠습니까?')
      if (answer) {
        next();
      } else {
        next(false);
      }
    }
  },
  computed: {
    ...mapState({
      userpk : (state) => state.userId,
    })
  },
};
</script>

<style></style>
