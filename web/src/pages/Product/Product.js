import React, { Component } from 'react';
import { Modal, Table, Button, Popconfirm, notification, Row, Col } from 'antd';
import AddProduct from './AddProduct';
import { deleteProduct } from '../../util/APIUtils';
import { API_PRODUCT_URL } from '../../constants/index';
import axios from 'axios';


import reqwest from 'reqwest';




class ProductShowList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: [],
      product: [],
      pagination: {},
      loading: false,
      visible: false,
      ModalText: AddProduct,
      confirmLoading: false,
    };

    this.columns = [
      {
        title: 'รูป',
        dataIndex: '',
        render: (record) => <img src={record.img} width="60px" height="60px" />
      },
      {
        title: 'ID',
        dataIndex: 'id',
        width: '20%',
      }, {
        title: 'ชื่อสินค้า',
        dataIndex: 'name',
      },
      {
        title: 'รายละเอียด',
        dataIndex: 'detail',
      },
      {
        title: 'ราคา',
        dataIndex: 'price',
      },
      {
        title: 'จำนวน',
        dataIndex: 'quantity',
      },
      {
        title: 'Action',
        dataIndex: '',
        key: 'x', render: (record) =>
          <Popconfirm title="ยืนยันการลบ?" onConfirm={() => deleteProduct(record.id)
            .then(response => {
              notification.success({
                message: 'Polling App',
                description: "ลบสินค้าเรียบร้อย!",
              });
              this.fetch();
            }).catch(error => {
              notification.error({
                message: 'Polling App',
                description: error.message || 'Sorry! Something went wrong. Please try again!'
              });
            })
          }>
            <a href="#">Delete</a>
          </Popconfirm>
        ,
      }];
  }

  showModal = () => {
    console.log('Clicked Modal');
    this.setState({
      visible: true,
    });
  }

  handleOk = () => {
    this.setState({
      ModalText: 'The modal will be closed after two seconds',
      confirmLoading: true,
    });
    setTimeout(() => {
      this.setState({
        visible: false,
        confirmLoading: false,
      });
    }, 2000);
  }

  handleCancel = () => {
    console.log('Clicked cancel button');
    this.setState({
      visible: false,
    });
  }

  componentDidMount() {
    this.fetch();
  }

  componentDidUpdate(nextProps) {
    if (this.props.isAuthenticated !== nextProps.isAuthenticated) {
      // Reset State
      this.setState({
        data: [],
        pagination: {},
        loading: false,
        visible: false,
        ModalText: AddProduct,
        confirmLoading: false,
      });
      this.fetch();
    }
  }


  handleTableChange = (pagination, filters, sorter) => {
    const pager = { ...this.state.pagination };
    pager.current = pagination.current;
    this.setState({
      pagination: pager,
    });
    this.fetch({
      results: pagination.pageSize,
      page: pagination.current,
      sortField: sorter.field,
      sortOrder: sorter.order,
      ...filters,
    });
  }

  fetch = (params = {}) => {
    console.log('params:', params);
    this.setState({ loading: true });
    reqwest({
      url: API_PRODUCT_URL,
      method: 'get',
      data: {},
      type: 'json',
    }).then((data) => {
      const pagination = { ...this.state.pagination };
      // Read total count from server
      // pagination.total = data.totalCount;
      pagination.total = 100;
      this.setState({
        loading: false,
        data: data,
        pagination,
      });
    });
  }

  getProductData = (productID) => {
    axios.get(API_PRODUCT_URL + '/' + productID)
    .then(response => {
        this.setState({
            product: response.data
        });
    })
    .catch(error => {
        console.log('Error fetching and parsing data', error);
    });
  };

  render() {
    const { visible, confirmLoading, ModalText } = this.state;
    const columns = this.columns;
    return (

      <div>
        <h1>จัดการรายการสินค้า</h1>
        <Row>
          <Col span={8}></Col>
          <Col span={8} offset={8} style={{ textAlign: 'right' }}>
            <div style={{ marginBottom: '20px' }}><Button type="primary" size="large" onClick={this.showModal}>+ เพิ่มสินค้าใหม่</Button></div>
          </Col>
        </Row>
        <Modal
          title="เพิ่มสินค้าใหม่"
          visible={visible}
          footer={null}
          onCancel={this.handleCancel}
        >
          <AddProduct ref="AddProduct" handleCancel={this.handleCancel} loadData={this.fetch} />
        </Modal>

        <Table
          onRow={(record, rowIndex) => {
            return {
              onClick: (event) => { 
                this.getProductData(record.id);

                <Modal
                title="เพิ่มสินค้าใหม่"
                visible={visible}
                footer={null}
                onCancel={this.handleCancel}>
              </Modal>
        
              },
            };
          }}
          columns={columns}
          rowKey={record => record.id}
          dataSource={this.state.data}
          pagination={this.state.pagination}
          loading={this.state.loading}
          onChange={this.handleTableChange}
        />
      </div>
    );
  }
}

export default ProductShowList;
