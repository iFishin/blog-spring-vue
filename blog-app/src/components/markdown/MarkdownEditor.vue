<!--
 * @Date: 2023-06-22 10:17:02
 * @LastEditors: Fishing yoo725@hotmail.com
 * @LastEditTime: 2023-06-23 09:15:50
 * @FilePath: \blog-app\src\components\markdown\MarkdownEditor.vue
 * @Description: https://github.com/iFishin
-->
<template>
  <div class="editor-container">
    <mavon-editor class="me-editor"
    ref="md"
    v-model="editor.value"
    @imgAdd="imgAdd"
    v-bind="editor">
    </mavon-editor>
  </div>
</template>


<script>

import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

import { upload } from '@/api/upload'

export default {
  name: 'MarkdownEditor',
  props: {
    editor: Object
  },
  data() {
    return {}
  },
  mounted() {
    this.$set(this.editor, 'ref', this.$refs.md)
  },
  methods: {
    imgAdd(pos, $file) {
      let that = this
      let formdata = new FormData();
      formdata.append('image', $file);

      upload(formdata).then(data => {
        // 第二步.将返回的url替换到文本原位置![...](./0) -> ![...](url)
        if (data.code == 0) {

          that.$refs.md.$img2Url(pos, data.data.url);
        } else {
          that.$message({ message: data.msg, type: 'error', showClose: true })
        }

      }).catch(err => {
        that.$message({ message: err, type: 'error', showClose: true });
      })
    }
  },
  components: {
    mavonEditor
  }
}
</script>
<style scoped>
.editor-container {
  display: flex;
  flex-direction: column;
  height: 100%;
  background: linear-gradient(120deg, #f6d365, #fda085);
}
.me-editor {
  flex: 1;
  z-index: 6 !important;
  transition: transform 0.5s ease-in-out;
}

.me-editor:hover {
  /* transform: scale(1.05); */
}

.v-note-wrapper.fullscreen {
  top: 60px !important
}
</style>
