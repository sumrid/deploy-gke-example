
import React from 'react'
import axios from 'axios';
import {Button, Upload, Icon } from 'antd';

class ImageUpload extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      file: null
    }
    this.onFormSubmit = this.onFormSubmit.bind(this)
    this.fileUpload = this.fileUpload.bind(this)
  }
  onFormSubmit(e) {
    e.preventDefault() // Stop form submit
    this.fileUpload(this.state.file).then((response) => {
      console.log(response.data);
    })
  }

  fileUpload(file) {
    const url = 'http://35.247.131.103/product/upload-image';
    const formData = new FormData();
    formData.append('file', file)
    const config = {
      headers: {
        'content-type': 'multipart/form-data'
      }
    }
    return axios.post(url, formData, config)
  }

  render() {
    return (
      <form >
        <input type="file" onChange={this.props.onFileChange} />
        </form>
    )
  }
}
export default ImageUpload