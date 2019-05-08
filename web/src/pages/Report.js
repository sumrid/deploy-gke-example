import React, { Component } from 'react';
import { Modal, Table, Button } from 'antd';
import Signup from '../user/signup/Signup';

import { API_REPORT_URL } from '../constants';

import reqwest from 'reqwest';

const columns = [{
  title: 'ID',
  dataIndex: 'id',
  width: '5%',
}, {
  title: 'วันที่',
  dataIndex: 'date',
},
{
  title: 'สินค้าขายดี',
  dataIndex: 'bestseller',
},
{
  title: 'จำนวน',
  dataIndex: 'income',
  width: '10%',
},
{
  title: 'Action',
  dataIndex: '',
  width: '10%',
  key: 'x', render: () => <a href="javascript:;">Delete</a>,
}];

class Dashboard extends Component {
  state = {
    data: [],
    pagination: {},
    loading: false,
    visible: false,
    ModalText: Signup,
    confirmLoading: false,
  };


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
      url: API_REPORT_URL,
      method: 'get',
      data: {},
      type: 'json',
    }).then((data) => {
      const pagination = { ...this.state.pagination };
      // Read total count from server
      // pagination.total = data.totalCount;
      pagination.total = 50;
      this.setState({
        loading: false,
        data: data,
        pagination,
      });
    });
  }

  render() {
    const { visible, confirmLoading, ModalText } = this.state;

    return (
      <div>
        <h1>Report Page (TODO..)</h1>
        <Table
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

export default Dashboard;
