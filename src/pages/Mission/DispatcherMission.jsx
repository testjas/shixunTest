import ProList from '@ant-design/pro-list';
import { Button, Space, Tag } from 'antd';
import React from 'react';
import userIcon from '../../assets/img/userIcon.svg';
export default function DispatcherMission() {
    const list = [{ title: "123", avatar: userIcon, description: "wqeqwe", subTitle: "wowo", status: "Error" },
    { title: "123", avatar: userIcon, description: "wqeqwe", subTitle: "wowo", status: "Error" },
    { title: "123", avatar: userIcon, description: "wqeqwe", subTitle: "wowo", status: "Error" }]

    return (
        <ProList
            toolBarRender={() => {
                return [
                    <Button key="3" type="primary">
                        新建任务
                    </Button>,
                ];
            }}
            search={(e) => {
                console.log(e)
            }}
            rowKey="name"
            FheaderTitle="你的任务列表"
            pagination={{
                pageSize: 5,
            }}
            dataSource={list}
            showActions="hover"
            metas={{
                title: {
                    dataIndex: 'user',
                    title: '用户',
                },
                avatar: {
                    dataIndex: 'avatar',
                    search: false,
                },
                description: {
                    dataIndex: 'title',
                    search: false,
                },
                subTitle: {
                    dataIndex: 'labels',
                    render: (_, row) => {
                        var _a;
                        return (<Space size={0}>
                            {(_a = row.labels) === null || _a === void 0 ? void 0 : _a.map((label) => (<Tag color="blue" key={label.name}>
                                {label.name}
                            </Tag>))}
                        </Space>);
                    },
                    search: false,
                },
                actions: {
                    render: (text, row) => [
                        <a href={row.url} target="_blank" rel="noopener noreferrer" key="link">
                            链路
                        </a>,
                        <a href={row.url} target="_blank" rel="noopener noreferrer" key="warning">
                            报警
                        </a>,
                        <a href={row.url} target="_blank" rel="noopener noreferrer" key="view">
                            查看
                        </a>,
                    ],
                    search: false,
                },
                status: {
                    // 自己扩展的字段，主要用于筛选，不在列表中显示
                    title: '状态',
                    valueType: 'select',
                    valueEnum: {
                        all: { text: '全部', status: 'Default' },
                        open: {
                            text: '未完成',
                            status: 'Error',
                        },
                        closed: {
                            text: '已完成',
                            status: 'Success',
                        },
                        processing: {
                            text: '已提交',
                            status: 'Processing',
                        },
                    },
                },
            }} />
    )
};