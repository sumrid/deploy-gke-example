import React, { Component } from 'react';
import { Modal, Table, Button, Popconfirm, notification, Row, Col } from 'antd';
import { deleteProduct } from '../../util/APIUtils';
import { API_PRODUCT_URL, API_USER_URL } from '../../constants/index';

import Signup from '../../user/signup/Signup';


import reqwest from 'reqwest';




class EmployeeList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: [],
            pagination: {},
            loading: false,
            visible: false,
            ModalText: 'test',
            confirmLoading: false,
        };

        this.columns = [
            {
                title: 'ชื่อ-นามสกุล',
                dataIndex: 'name',
            },
            {
                title: 'Username',
                dataIndex: 'username',
            },
            {
                title: 'อีเมล์',
                dataIndex: 'email',
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
                ModalText: "Test",
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
            url: 'http://localhost:5000/api/users/all',
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
        const columns = this.columns;
        return (

            <div>
                <h1>จัดการรายชื่อพนักงาน</h1>
                <Row>
          <Col span={8}></Col>
          <Col span={8} offset={8} style={{textAlign: 'right'}}>
            <div style={{ marginBottom: '20px' }}><Button type="primary" size="large" onClick={this.showModal}>+ เพิ่มพนักงานใหม่</Button></div>
          </Col>
        </Row>
                <Modal
                    title="เพิ่มพนักงานใหม่"
                    visible={visible}
                    footer={null}
                    onCancel={this.handleCancel}
                >
                <Signup handleCancel={this.handleCancel} loadData={this.fetch}  />
                </Modal>

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

export default EmployeeList;
