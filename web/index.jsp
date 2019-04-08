<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <style>
        table {
            text-align: center;
        }
    </style>
    <title>Status page</title>
</head>
<body>
<c:import url="/main"/>
<h1 align="center">University</h1>
<table align="center" border="1" cellpadding="5">
    <caption><h2>Students</h2></caption>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Last name</th>
        <th>Age</th>
        <th>Student group</th>
    </tr>
    <c:forEach var="student" items="${university.students}">
        <tr>
            <td>${student.id}</td>
            <td>${student.name}</td>
            <td>${student.lastName}</td>
            <td>${student.age}</td>
            <td>${student.studentGroup}</td>
        </tr>
    </c:forEach>
</table>
<table align="center" border="1" cellpadding="5">
    <caption><h2>Lecturers</h2></caption>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Last name</th>
        <th>Age</th>
        <th>Department</th>
    </tr>
    <c:forEach var="lecturer" items="${university.lecturers}">
        <tr>
            <td>${lecturer.id}</td>
            <td>${lecturer.name}</td>
            <td>${lecturer.lastName}</td>
            <td>${lecturer.age}</td>
            <td>${lecturer.department}</td>
        </tr>
    </c:forEach>
</table>
<table align="center" border="1" cellpadding="5">
    <caption><h2>Lessons</h2></caption>
    <tr>
        <th>ID</th>
        <th>Subject</th>
        <th>Classroom</th>
        <th>Lecturer</th>
        <th>Start time</th>
        <th>End time</th>
        <th>Students</th>
    </tr>
    <c:forEach var="lesson" items="${university.lessons}">
        <tr>
            <td>${lesson.id}</td>
            <td>${lesson.subject}</td>
            <td>${lesson.classroom}</td>
            <td>${lesson.lecturer.name} ${lesson.lecturer.lastName}</td>
            <td>${lesson.timePeriod.startTime}</td>
            <td>${lesson.timePeriod.endTime}</td>
            <td>
                <table cellpadding="5">
                    <c:forEach var="student" items="${lesson.students}">
                        <tr>
                            <td>${student.name} ${student.lastName} ${student.studentGroup}</td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
