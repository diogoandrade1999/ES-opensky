$(document).ready(function(){
    $("#airportsearch").on('input', function(e) {
        keyword = $('#airportsearch').val();

        if (keyword.length > 0) {
            $.ajax({
                url: '/airports',
                data: { "keyword": keyword },
                method: 'POST'
            })
            .done((res) => {
                console.log(res);
                $("#airports").empty();

                var text = '';
                var index = 0;
                res.forEach(element => {
                    if (index % 3 == 0) {
                        text += '<div class="row mt-4">';
                    }
                    var name = element['name'];
                    var code = element['code'];
                    var country = element['country'];
                    var airport = `<div class="col-4">
                                        <div class="card text-dark">
                                            <div class="card-body">
                                                <h5 class="card-title">` + name + `</h5>
                                                <p class="card-text">` + code + `</p>
                                                <p class="card-text">` + country + `</p>
                                                <a href="/flights/` + code + `" class="btn btn-primary">Flights</a>
                                            </div>
                                        </div>
                                    </div>`;
                    text += airport;
                    if (index % 3 == 2) {
                        text += '</div>';
                    }
                    index++;
                });
                if (index % 3 != 0) {
                    text += '</div>';
                }
                $("#airports").append(text);
            })
            .fail((err) => {
                console.log(err);
            });
        }
    });
});