# Generated by Django 3.1.6 on 2021-02-22 03:10

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('hospitals', '0005_auto_20210222_0307'),
    ]

    operations = [
        migrations.AlterField(
            model_name='status',
            name='name',
            field=models.CharField(max_length=100, primary_key=True, serialize=False),
        ),
    ]
