import React, { Component } from 'react';
import { addNewProduct } from '../../util/APIUtils';
import ImageUpload from '../ImageUpload';
import './form-style.css';
import axios from 'axios';

import {
    PRODUCT_NAME_MIN_LENGTH,
    PRODUCT_NAME_MAX_LENGTH,
    DETAIL_MAX_LENGTH

} from '../../constants';

import { Form, Input, Button, notification, Upload, Icon } from 'antd';
const FormItem = Form.Item;

class AddProduct extends Component {
    constructor(props) {
        super(props);
        this.state = {
            name: {
                value: ''
            },
            detail: {
                value: ''
            },
            price: {
                value: ''
            },
            quantity: {
                value: ''
            },
            image: {
                value: ''
            },
            file: null
        }
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.isFormInvalid = this.isFormInvalid.bind(this);

        this.fileUpload = this.fileUpload.bind(this)
        this.onFileChange = this.onFileChange.bind(this)

    }


    onFileChange(e) {
        this.setState({ file: e.target.files[0] })
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


    handleInputChange(event, validationFun) {
        const target = event.target;
        const inputName = target.name;
        const inputValue = target.value;

        this.setState({
            [inputName]: {
                value: inputValue,
                ...validationFun(inputValue)
            }
        });
    }

    handleSubmit(event) {
        event.preventDefault();

        this.fileUpload(this.state.file).then((response) => {
            console.log(response.data);

            const AddProductRequest = {
                name: this.state.name.value,
                price: this.state.price.value,
                detail: this.state.detail.value,
                quantity: this.state.quantity.value,
                img: "http://35.247.131.103/product/image-name/" + response.data
            };
    
            addNewProduct(AddProductRequest)
                .then(response => {
                    notification.success({
                        message: 'Polling App',
                        description: "เพิ่มสินค้าใหม่เรียบร้อย!",
                    });
                    this.props.loadData();
                    this.props.handleCancel();
                }).catch(error => {
                    notification.error({
                        message: 'Polling App',
                        description: error.message || 'Sorry! Something went wrong. Please try again!'
                    });
                });
        });

       

        
    }

    isFormInvalid() {
        return !(this.state.name.validateStatus === 'success' &&
            this.state.detail.validateStatus === 'success' &&
            this.state.price.validateStatus === 'success' &&
            this.state.quantity.validateStatus === 'success' &&
            this.state.file != null
        );
    }

    render() {
        return (
            <div className="a-container">
                <div className="a-content">
                    <Form id="add-form" onSubmit={this.handleSubmit} className="a-form">
                        <ImageUpload onFileChange={this.onFileChange} />
                        <FormItem
                            label="ชื่อสินค้า"
                            validateStatus={this.state.name.validateStatus}
                            help={this.state.name.errorMsg}>
                            <Input
                                size="large"
                                name="name"
                                autoComplete="off"
                                placeholder="กรอกชื่อสินค้า"
                                value={this.state.name.value}
                                onChange={(event) => this.handleInputChange(event, this.validateName)} />
                        </FormItem>
                        <FormItem label="รายละเอียด"
                            hasFeedback
                            validateStatus={this.state.detail.validateStatus}
                            help={this.state.detail.errorMsg}>
                            <Input
                                size="large"
                                name="detail"
                                autoComplete="off"
                                placeholder="กรอกรายละเอียดเพิ่มเติมของสินค้า"
                                value={this.state.detail.value}
                                onChange={(event) => this.handleInputChange(event, this.validatedetail)} />
                        </FormItem>
                        <FormItem label="ราคา"
                            hasFeedback
                            validateStatus={this.state.price.validateStatus}
                            help={this.state.price.errorMsg}>
                            <Input
                                size="large"
                                name="price"
                                autoComplete="off"
                                placeholder="กรอกราคาสินค้า"
                                value={this.state.price.value}
                                onChange={(event) => this.handleInputChange(event, this.validateprice)} />
                        </FormItem>
                        <FormItem label="จำนวน"
                            hasFeedback
                            validateStatus={this.state.quantity.validateStatus}
                            help={this.state.quantity.errorMsg}>
                            <Input
                                size="large"
                                name="quantity"
                                autoComplete="off"
                                placeholder="กรอกจำนวนสินค้า"
                                value={this.state.quantity.value}
                                onChange={(event) => this.handleInputChange(event, this.validatequantity)} />
                        </FormItem>
                        <FormItem>
                            <Button type="primary"
                                htmlType="submit"
                                size="large"
                                className="a-form-button"
                                disabled={this.isFormInvalid()}>บันทึกข้อมูล</Button>
                        </FormItem>
                    </Form>
                </div>
            </div>
        );
    }

    // Validation Functions

    validateName = (name) => {
        if (name.length < PRODUCT_NAME_MIN_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Name is too short (Minimum ${PRODUCT_NAME_MIN_LENGTH} characters needed.)`
            }
        } else if (name.length > PRODUCT_NAME_MAX_LENGTH) {
            return {
                validationStatus: 'error',
                errorMsg: `Name is too long (Maximum ${PRODUCT_NAME_MAX_LENGTH} characters allowed.)`
            }
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null,
            };
        }
    }

    validateprice = (price) => {
        if (!price) {
            return {
                validateStatus: 'error',
                errorMsg: 'ราคาสินค้า ห้ามเว้นว่าง'
            }
        }

        if (isNaN(price)) {
            return {
                validateStatus: 'error',
                errorMsg: 'ใส่ตัวเลขเท่านั้น'
            }
        } else if (price < 1) {
            return {
                validateStatus: 'error',
                errorMsg: `กรุณาใส่จำนวนสินค้าอย่างน้อย 1 ชิ้นขึ้นไป`
            }
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null
            }
        }
    }

    validatedetail = (detail) => {
        if (detail.length > DETAIL_MAX_LENGTH) {
            return {
                validationStatus: 'error',
                errorMsg: `detail is too long (Maximum ${DETAIL_MAX_LENGTH} characters allowed.)`
            }
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null
            }
        }
    }



    validatequantity = (quantity) => {
        if (isNaN(quantity)) {
            return {
                validateStatus: 'error',
                errorMsg: 'ใส่ตัวเลขเท่านั้น'
            }
        }
        if (quantity < 1) {
            return {
                validateStatus: 'error',
                errorMsg: `กรุณาใส่จำนวนสินค้าอย่างน้อย 1 ชิ้นขึ้นไป`
            }
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null,
            };
        }
    }

}

export default AddProduct;