<html>
    <body>
        <div>
            <form method="post">
                <input type="text" name="name" placeholder="Введите название занятия" />
                <button type="submit">Добавить занятие</button>
            </form>
        </div>

        <div>Список мероприятий</div>
        <#list activities as activity>
            <div>
                <b>${activity.id}</b>
                <b>${activity.name}</b>
            </div>
        </#list>
    </body>
</html>